package dev.patkub.app.itemmanagement.service;

import dev.patkub.app.TestcontainersConfiguration;
import dev.patkub.app.itemmanagement.domain.Item;
import dev.patkub.app.itemmanagement.domain.ItemRepository;
import dev.patkub.app.security.dev.SampleUsers;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Transactional
class ItemServiceTest {

    @Autowired
    ItemService itemService;

    @Autowired
    ItemRepository itemRepository;

    @Test
    @WithUserDetails(SampleUsers.USER_USERNAME)
    public void items_are_stored_in_the_database() {
        var listed = LocalDate.of(2025, 2, 7);
        var price = Double.parseDouble("105.15");
        itemService.createItem("New item", listed, price);
        assertThat(itemService.list(PageRequest.ofSize(1))).singleElement()
                .matches(item -> item.getName().equals("New item") && listed.equals(item.getListedDate()));
    }

    @Test
    @WithUserDetails(SampleUsers.ADMIN_USERNAME)
    public void items_are_validated_before_they_are_stored() {
        assertThatThrownBy(() -> itemService.createItem("X".repeat(Item.NAME_MAX_LENGTH + 1), null, null))
                .isInstanceOf(ValidationException.class);
    }
}
