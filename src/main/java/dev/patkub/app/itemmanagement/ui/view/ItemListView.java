package dev.patkub.app.itemmanagement.ui.view;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.NumberField;
import dev.patkub.app.base.ui.component.ViewToolbar;
import dev.patkub.app.itemmanagement.domain.Item;
import dev.patkub.app.itemmanagement.service.ItemService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Optional;

import static com.vaadin.flow.spring.data.VaadinSpringDataHelpers.toSpringPageRequest;

@Route("items")
@PageTitle("Items")
@Menu(order = 0, icon = "vaadin:package", title = "Items")
@PermitAll // When security is enabled, allow all authenticated users
public class ItemListView extends Main {

    private final ItemService itemService;

    final TextField name;
    final DatePicker listedDate;
    final NumberField price;
    final Button createBtn;
    final Grid<Item> itemGrid;

    public ItemListView(ItemService itemService) {
        this.itemService = itemService;

        name = new TextField();
        name.setPlaceholder("Enter item name");
        name.setAriaLabel("Item name");
        name.setMaxLength(Item.NAME_MAX_LENGTH);
        name.setMinWidth("20em");

        listedDate = new DatePicker();
        listedDate.setPlaceholder("Listed date");
        listedDate.setAriaLabel("Listed date");

        price = new NumberField();
        name.setAriaLabel("Price");
        price.setValue(0.00);
        Div dollarPrefix = new Div();
        dollarPrefix.setText("$");
        price.setPrefixComponent(dollarPrefix);

        createBtn = new Button("Create", event -> createItem());
        createBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        var dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(getLocale());
        var priceFormatter = new DecimalFormat("$ #,##0.00");

        itemGrid = new Grid<>();
        itemGrid.setItems(query -> itemService.list(toSpringPageRequest(query)).stream());
        itemGrid.addColumn(Item::getName).setHeader("Name");
        itemGrid.addColumn(item -> Optional.ofNullable(item.getListedDate()).map(dateFormatter::format).orElse("Never"))
                .setHeader("Listed Date");
        itemGrid.addColumn(item -> Optional.ofNullable(item.getPrice()).map(priceFormatter::format).orElse("$0.00"))
            .setHeader("Price");
        itemGrid.setSizeFull();

        setSizeFull();
        addClassNames(LumoUtility.BoxSizing.BORDER, LumoUtility.Display.FLEX, LumoUtility.FlexDirection.COLUMN,
                LumoUtility.Padding.MEDIUM, LumoUtility.Gap.SMALL);

        add(new ViewToolbar("Item List", ViewToolbar.group(name, listedDate, price, createBtn)));
        add(itemGrid);
    }

    private void createItem() {
        itemService.createItem(name.getValue(), listedDate.getValue(), price.getValue());
        itemGrid.getDataProvider().refreshAll();
        name.clear();
        listedDate.clear();
        price.clear();
        Notification.show("Item added", 3000, Notification.Position.BOTTOM_END)
                .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }

}
