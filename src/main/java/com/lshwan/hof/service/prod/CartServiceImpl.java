package com.lshwan.hof.service.prod;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.lshwan.hof.domain.dto.prod.CartDto;
import com.lshwan.hof.domain.entity.common.FileMaster;
import com.lshwan.hof.domain.entity.member.Member;
import com.lshwan.hof.domain.entity.prod.Cart;
import com.lshwan.hof.domain.entity.prod.Prod;
import com.lshwan.hof.domain.entity.prod.ProdOption;
import com.lshwan.hof.domain.entity.prod.ProdOptionMap;
import com.lshwan.hof.mapper.CartMapper;
import com.lshwan.hof.repository.common.FileMasterRepository;
import com.lshwan.hof.repository.member.MemberRepository;
import com.lshwan.hof.repository.prod.CartRepository;
import com.lshwan.hof.repository.prod.ProdOptionRepository;
import com.lshwan.hof.repository.prod.ProdRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@Transactional
@AllArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    private final ProdRepository prodRepository;
    private final ProdOptionRepository prodOptionRepository;
    private final FileMasterRepository fileMasterRepository;
    private final CartMapper cartMapper;

    /**
     * 1. 장바구니 담기 (옵션 포함)
     */
    @Override
    public CartDto addToCart(CartDto cartDto) {
        log.info("장바구니 담기 요청: {}", cartDto);

        // 회원 조회
        Member member = memberRepository.findById(cartDto.getMno())
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

        // 상품 조회
        Prod prod = prodRepository.findById(cartDto.getPno())
                .orElseThrow(() -> new IllegalArgumentException("상품 정보를 찾을 수 없습니다."));

        // 상품 옵션 조회 (옵션이 있을 경우)
        ProdOption prodOption = null;
        if (cartDto.getOptions() != null && !cartDto.getOptions().isEmpty()) {
            Long optionNo = cartDto.getOptions().get(0).getOptionNo();
            prodOption = prodOptionRepository.findById(optionNo)
                    .orElseThrow(() -> new IllegalArgumentException("상품 옵션을 찾을 수 없습니다."));
        }

        // Cart 엔티티 생성
        Cart cart = Cart.builder()
                .member(member)
                .prod(prod)
                .prodOption(prodOption)
                .count(1)
                .build();

        // 저장
        cartRepository.save(cart);

        // 이미지 URL 가져오기
        List<FileMaster> images = fileMasterRepository.findByProdPnoAndFileType(prod.getPno(), FileMaster.FileType.prod_main);
        List<String> imageUrls = images.stream().map(FileMaster::getUrl).collect(Collectors.toList());

        // 반환할 DTO 생성
        return CartDto.builder()
                .mno(member.getMno())
                .pno(prod.getPno())
                .title(prod.getTitle())
                .price(prod.getPrice())
                .imageUrls(imageUrls)
                .options(cartDto.getOptions())
                .build();
    }

    /**
     * 2. 장바구니 조회 (옵션 및 이미지 포함)
     */
    @Override
    public List<CartDto> getCartItems(Long mno) {
        log.info("장바구니 조회 요청 - 회원 번호: {}", mno);

        // 장바구니 항목 조회
        List<Cart> cartList = cartRepository.findAllByMember_Mno(mno);

        return cartList.stream().map(cart -> {
          // ✅ 상품 이미지 조회
          List<FileMaster> images = fileMasterRepository.findByProdPnoAndFileType(cart.getProd().getPno(), FileMaster.FileType.prod_main);
          List<String> imageUrls = images.stream()
                  .map(FileMaster::getUrl)
                  .collect(Collectors.toList());
      
          // ✅ 상품 정보 조회
          Prod product = prodRepository.findById(cart.getProd().getPno())
                  .orElseThrow(() -> new RuntimeException("Product not found"));
      
          // ✅ 옵션 리스트 조회 (상품에 연결된 옵션들)
          List<ProdOption> optionList = prodOptionRepository.findByOptionMapsProdPno(product.getPno());
      
          // ✅ 옵션 정보 변환 (ProdOption -> CartDto.ProdOptionDto)
          List<CartDto.ProdOptionDto> options = optionList.stream()
                  .map(option -> CartDto.ProdOptionDto.builder()
                          .optionNo(option.getNo())  // ✅ 옵션 번호
                          .type(option.getType())    // ✅ 옵션 타입 (예: 사이즈, 색상)
                          .value(option.getValue())  // ✅ 옵션 값 (예: 270mm, 빨강)
                          .addPrice(option.getAddPrice())  // ✅ 추가 금액
                          .stock(option.getOptionMaps().stream()
                                  .mapToInt(ProdOptionMap::getStock)
                                  .sum())  // ✅ 옵션 재고 합계
                          .build())
                  .collect(Collectors.toList());
      
          // ✅ CartDto 생성 및 반환
          return CartDto.builder()
                  .cartNo(cart.getNo())
                  .mno(cart.getMember().getMno())
                  .pno(cart.getProd().getPno())
                  .title(cart.getProd().getTitle())
                  .price(cart.getProd().getPrice())
                  .imageUrls(imageUrls)
                  .options(options)
                .build();
      }).collect(Collectors.toList());
      
    }

    /**
     * 3. 장바구니 항목 수정 (옵션/수량 변경)
     */
    @Override
    public CartDto updateCartItem(Long cartId, CartDto cartDto) {
      log.info("장바구니 항목 수정 요청 - cartId: {}, 수정 데이터: {}", cartId, cartDto);

      // ✅ 장바구니 항목 조회
      Cart cart = cartRepository.findById(cartId)
              .orElseThrow(() -> new IllegalArgumentException("장바구니 항목을 찾을 수 없습니다."));
  
      // ✅ 옵션 변경
      List<CartDto.ProdOptionDto> optionsDto = cartDto.getOptions();
      if (optionsDto != null && !optionsDto.isEmpty()) {
          Long optionNo = optionsDto.get(0).getOptionNo();
          ProdOption prodOption = prodOptionRepository.findById(optionNo)
                  .orElseThrow(() -> new IllegalArgumentException("상품 옵션을 찾을 수 없습니다."));
          cart.setProdOption(prodOption);
      } else {
          // 옵션이 없으면 null 처리
          cart.setProdOption(null);
      }
  
      // ✅ 수량 변경 (CartDto에 수량 정보가 있다면 사용)
      if (cartDto.getCount() > 0) {
          cart.setCount(cartDto.getCount());
      } else {
          // 기본값으로 1 설정
          cart.setCount(1);
      }
  
      // ✅ 장바구니 항목 업데이트
      Cart updatedCart = cartRepository.save(cart);
  
      // ✅ 이미지 URL 가져오기
      List<FileMaster> images = fileMasterRepository.findByProdPnoAndFileType(updatedCart.getProd().getPno(), FileMaster.FileType.prod_main);
      List<String> imageUrls = images.stream()
              .map(FileMaster::getUrl)
              .collect(Collectors.toList());
  
      // ✅ 옵션 정보 변환
      List<CartDto.ProdOptionDto> updatedOptions = updatedCart.getProdOption() != null ?
              List.of(CartDto.ProdOptionDto.builder()
                      .optionNo(updatedCart.getProdOption().getNo())
                      .type(updatedCart.getProdOption().getType())
                      .value(updatedCart.getProdOption().getValue())
                      .addPrice(updatedCart.getProdOption().getAddPrice())
                      .stock(updatedCart.getProdOption().getOptionMaps().stream()
                              .mapToInt(ProdOptionMap::getStock)
                              .sum())
                      .build())
              : List.of();
  
      // ✅ CartDto 반환
      return CartDto.builder()
              .mno(updatedCart.getMember().getMno())
              .pno(updatedCart.getProd().getPno())
              .title(updatedCart.getProd().getTitle())
              .price(updatedCart.getProd().getPrice())
              .imageUrls(imageUrls)
              .options(updatedOptions)
              .build();
    }

    /**
     * 4. 장바구니 항목 삭제
     */
    @Override
    public void deleteCartItem(Long cartId) {
        log.info("장바구니 항목 삭제 요청 - cartId: {}", cartId);

        if (!cartRepository.existsById(cartId)) {
            throw new IllegalArgumentException("장바구니 항목을 찾을 수 없습니다.");
        }

        cartRepository.deleteById(cartId);
    }
    @Override
    public void deleteCartItemList(Long mno) {
        log.info("장바구니 항목 삭제 요청이다 - cartId: {}", mno);
        cartMapper.deleteByMemberMno(mno);
    }
        /**
     * 5. 장바구니 임시저장
     */
    @Override
    public void saveCartItems(List<CartDto> cartItems) {
        log.info("장바구니 임시저장 요청: {}", cartItems);

        for (CartDto cartDto : cartItems) {
            if (cartDto.getCartNo() != null) {
                // 기존 항목 업데이트
                updateCartItem(cartDto.getCartNo(), cartDto);
            } else {
                // 새로운 항목 추가
                addToCart(cartDto);
            }
        }
    }
}
