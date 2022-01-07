# SpringDataJpa_shoppingmall
**SpringDataJpa**를 공부하면서 쇼핑몰 웹앱을 만들어보았습니다. Inflearn 김영한님의 **"실전! 스프링 부트와 JPA 활용1" 을 공부하며 따라 만든 프로젝트**입니다.
**상품을 등록하고 등록된 상품을 주문, 취소, 조회** 하는 기능을 간단하게 만들었습니다.

<br>

## 프로젝트 목표
1:1, N:1, N:M 엔티티 관계를 설계하여 설계한 대로 구현해본다. 
<br><br>

## 기능 요구사항
- 회원 기능
  - 회원 등록
  - 회원 조회
- 상품 기능
  - 상품 등록
  - 상품 수정
  - 상품 조회
- 주문 기능
  - 상품 주문
  - 주문 내역 조회
  - 주문 취소
- 기타 요구사항
  - 상품은 재고 관리가 필요하다.
  - 상품의 종류는 도서, 음반, 영화가 있다.
  - 상품을 카테고리로 구분할 수 있다.
  - 상품 주문시 배송 정보를 입력할 수 있다.

<br>

## 도메인 모델
![image](https://user-images.githubusercontent.com/53790137/148490401-400a0ccd-b4a4-4cd5-9eba-c8d897c9201b.png)

<br>

## 테이블 설계
![image](https://user-images.githubusercontent.com/53790137/148490489-0dbd24d1-18e9-45a1-8242-cd276e91c0b1.png)

<br>

## 시연 영상
- **상품 등록 & 조회**
<img src="https://user-images.githubusercontent.com/53790137/148493767-fd1802ec-c55b-467a-b338-3baa5d2fa9b5.gif" width="500" height="400">

- **등록된 상품 수정**
<img src="https://user-images.githubusercontent.com/53790137/148495114-07d15cb4-193e-42ee-854d-a3eabd2a5111.gif" width="500" height="400">

- **상품 주문**
<img src="https://user-images.githubusercontent.com/53790137/148495460-da216d38-09f7-407b-b9f5-ac9ed829136a.gif" width="500" height="400">

- **상품 주문내역 조회 & 취소**
<img src="https://user-images.githubusercontent.com/53790137/148496219-3884cc87-a61a-456d-845b-14c1a998a70b.gif" width="500" height="400">
