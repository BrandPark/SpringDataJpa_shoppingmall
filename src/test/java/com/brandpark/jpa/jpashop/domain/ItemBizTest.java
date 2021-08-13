package com.brandpark.jpa.jpashop.domain;

import com.brandpark.jpa.jpashop.domain.items.Book;
import com.brandpark.jpa.jpashop.domain.items.Item;
import com.brandpark.jpa.jpashop.exception.NotEnoughStockException;
import org.junit.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

public class ItemBizTest {
    @Test
    public void 재고가_감소된다() throws Exception {
        // given
        Item item = createBook(100);
        int saveStock = item.getStockQuantity();

        // when
        int removeQuantity = 10;
        item.removeStock(removeQuantity);

        // then
        int modStock = item.getStockQuantity();
        assertThat(saveStock).isGreaterThan(0);
        assertThat(modStock).isEqualTo(saveStock - removeQuantity);
    }

    @Test
    public void 재고가_증가된다() throws Exception {
        // given
        Item item = createBook(100);
        int saveStock = item.getStockQuantity();

        // when
        int addQuantity = 10;
        item.addStock(addQuantity);

        // then
        int modStock = item.getStockQuantity();
        assertThat(saveStock).isGreaterThan(0);
        assertThat(modStock).isEqualTo(saveStock + addQuantity);
    }

    @Test(expected = NotEnoughStockException.class)
    public void 감소량이_재고보다_많으면_예외발생() throws Exception {
        // given
        Item item = createBook(100);
        int saveStock = item.getStockQuantity();

        // when
        int removeQuantity = saveStock + 10;
        item.removeStock(removeQuantity);

        // then
        fail("예외가 발생해야 한다.");
    }

    private Book createBook(int quantity) {
        return Book.builder()
                .name("책1")
                .price(10000)
                .author("mingon")
                .stockQuantity(quantity)
                .isbn("isbn_num0")
                .build();
    }
}