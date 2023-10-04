package cz.upce.frontend.users;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteParameters;
import com.vaadin.flow.theme.lumo.LumoUtility;
import cz.upce.frontend.Menu;
import cz.upce.nnpro_backend.dtos.*;
import cz.upce.nnpro_backend.services.UserService;

import javax.annotation.security.PermitAll;

@Route(value = "users/:userID?/:action?(edit)", layout = Menu.class)
@PermitAll

public class UserList extends Composite<VerticalLayout> {
    private final Grid<UserOutDto> grid = new Grid<>(UserOutDto.class, false);

    private final UserService userService;


    public UserList(UserService userService) {
        this.userService = userService;
        getContent().addClassNames("master-detail-view");

        VerticalLayout verticalLayoutMain = new VerticalLayout();
        Button buttonAddUser = new Button("Přidat uživatele");
        buttonAddUser.addClickListener(event -> {
            buttonAddUser.getUI().ifPresent(ui -> ui.navigate(UserDetail.class));

        });
        verticalLayoutMain.add(buttonAddUser);
        buttonAddUser.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        createGridLayout(verticalLayoutMain);
        getContent().add(verticalLayoutMain);
        configureGrid();
    }

    private void createGridLayout(VerticalLayout horizontalLayout) {
        getContent().setHeightFull();
        getContent().setWidthFull();
        horizontalLayout.setWidthFull();
        getContent().setFlexGrow(1.0, horizontalLayout);
        horizontalLayout.addClassName(LumoUtility.Gap.MEDIUM);
        horizontalLayout.setFlexGrow(1.0, grid);
        grid.setHeightFull();
        horizontalLayout.add(grid);
    }

    private void configureGrid() {
        grid.addColumn(UserOutDto::getUsername).setAutoWidth(true).setHeader("Uživatelské jméno").setSortable(true).setComparator(item -> item.getUsername().toUpperCase());
        grid.addColumn(UserOutDto::getEmail).setAutoWidth(true).setHeader("Email").setSortable(true).setComparator(item -> item.getEmail().toUpperCase());
        grid.addColumn(UserOutDto::getJobPosition).setAutoWidth(true).setHeader("Pozice").setSortable(true);
        grid.addColumn(item -> item.getRole().getDescription()).setAutoWidth(true).setHeader("Role").setSortable(true).setComparator(item -> item.getRole().getDescription());
        grid.addColumn(item -> item.getBranchOfficeDto().getCity()).setAutoWidth(true).setHeader("Stanice").setSortable(true).setComparator(item -> item.getBranchOfficeDto().getCity());

        grid.setItems(userService.getAllUsers());
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
    }

}
