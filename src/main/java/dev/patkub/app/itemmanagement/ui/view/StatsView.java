package dev.patkub.app.itemmanagement.ui.view;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import dev.patkub.app.itemmanagement.service.ItemService;
import jakarta.annotation.security.PermitAll;

import java.text.DecimalFormat;
import java.util.Optional;

@Route("stats")
@PageTitle("Statistics")
@Menu(order = 0, icon = "vaadin:line-bar-chart", title = "Statistics")
@PermitAll // When security is enabled, allow all authenticated users
public class StatsView extends Main {

    private final ItemService itemService;
    final Grid<Stat> statsGrid;

    public StatsView(ItemService itemService) {
        this.itemService = itemService;

        var priceFormatter = new DecimalFormat("$ #,##0.00");

        statsGrid = new Grid<>();
        statsGrid.setItems(
                new Stat("Profit", Optional.of(this.itemService.totalPrice()).map(priceFormatter::format).orElse("$0.00"))
        );
        statsGrid.addColumn(Stat::getName).setHeader("Statistic");
        statsGrid.addColumn(item -> Optional.of(item.getValue()).orElse("Unknown"))
                .setHeader("Value");
        statsGrid.setSizeFull();

        add(statsGrid);
    }


}
