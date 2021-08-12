package com.brandpark.jpa.jpashop.service;

import com.brandpark.jpa.jpashop.domain.items.Book;
import com.brandpark.jpa.jpashop.domain.items.Item;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemServiceTest {
    @Autowired
    ItemService itemService;

    @Autowired
    EntityManager em;

    @Test
    public void 책이_등록된다() throws Exception {
        // given
        Book book = createBook();

        // when
        Long saveId = itemService.saveItem(book);

        // then
        em.flush();
        em.clear();
        Book findBook = em.find(Book.class, saveId);
        assertThat(book.getName()).as("이름").isEqualTo(findBook.getName());
        assertThat(book.getPrice()).as("가격").isEqualTo(findBook.getPrice());
        assertThat(book.getStockQuantity()).as("재고").isEqualTo(findBook.getStockQuantity());
        assertThat(book.getAuthor()).as("저자").isEqualTo(findBook.getAuthor());
        assertThat(book.getIsbn()).as("ISBN").isEqualTo(findBook.getIsbn());
    }

    @Test
    public void 상품이_모두_조회된다() throws Exception {
        // given
        Book book1 = createBook();
        em.persist(book1);
        Book book2 = createBook();
        em.persist(book2);

        List<Item> items = new ArrayList<>(List.of(book1, book2));

        // when
        List<Item> findItems = itemService.findItems();

        // then
        assertThat(findItems.size()).as("item 개수").isEqualTo(items.size());
    }

    @Test
    public void 상품이_하나_조회된다() throws Exception {
        // given
        Book book = createBook();
        em.persist(book);

        // when
        em.flush();
        em.clear();
        Book findItem = (Book)itemService.findOne(book.getId());

        // then
        assertThat(book.getName()).as("이름").isEqualTo(findItem.getName());
        assertThat(book.getPrice()).as("가격").isEqualTo(findItem.getPrice());
        assertThat(book.getStockQuantity()).as("재고").isEqualTo(findItem.getStockQuantity());
        assertThat(book.getAuthor()).as("저자").isEqualTo(findItem.getAuthor());
        assertThat(book.getIsbn()).as("ISBN").isEqualTo(findItem.getIsbn());
    }

    private Book createBook() {
        return Book.builder()
                .name("책1")
                .price(10000)
                .author("mingon")
                .stockQuantity(100)
                .isbn("isbn_num0")
                .build();
    }
}