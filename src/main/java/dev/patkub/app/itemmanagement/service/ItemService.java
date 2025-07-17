package dev.patkub.app.itemmanagement.service;

import dev.patkub.app.itemmanagement.domain.Item;
import dev.patkub.app.itemmanagement.domain.ItemRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@PreAuthorize("isAuthenticated()")
public class ItemService {

    private final ItemRepository itemRepository;

    ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Transactional
    public void createItem(String name, @Nullable LocalDate listedDate, @Nullable Double price) {
        if ("fail".equals(name)) {
            throw new RuntimeException("This is for testing the error handler");
        }
        var item = new Item();
        item.setName(name);
        item.setListedDate(listedDate);
        item.setPrice(price);
        itemRepository.saveAndFlush(item);
    }

    @Transactional(readOnly = true)
    public List<Item> list(Pageable pageable) {
        return itemRepository.findAllBy(pageable).toList();
    }

    @Transactional(readOnly = true)
    public Double totalPrice() {
        double sum = itemRepository.findAll().stream().map(Item::getPrice)
                // Filter out null values
                .filter(Objects::nonNull)
                // Map to primitive double
                .mapToDouble(Double::doubleValue)
                // Calculate the sum
                .sum();

        return sum;
    }

}
