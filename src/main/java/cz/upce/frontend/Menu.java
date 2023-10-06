package cz.upce.frontend;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import cz.upce.frontend.office.OfficeListDetail;
import cz.upce.frontend.cars.CarsList;
import cz.upce.frontend.owners.OwnerListDetail;
import cz.upce.frontend.users.UserList;
import cz.upce.nnpro_backend.security.SecurityService;

import javax.annotation.security.PermitAll;

@PermitAll
public class Menu extends AppLayout {

    private final SecurityService securityService;

    public Menu(SecurityService securityService) {
        this.securityService = securityService;
        HorizontalLayout horizontalLayoutMain = new HorizontalLayout();
        VerticalLayout verticalLayoutLeft = new VerticalLayout();
        VerticalLayout verticalLayoutMiddle = new VerticalLayout();
        VerticalLayout verticalLayoutRight = new VerticalLayout();
        horizontalLayoutMain.setWidthFull();
        horizontalLayoutMain.addClassNames(LumoUtility.Gap.MEDIUM);
        verticalLayoutLeft.setWidth(null);
        verticalLayoutRight.setWidth(null);
        verticalLayoutMiddle.setWidth(null);
        horizontalLayoutMain.setFlexGrow(1.0, verticalLayoutMiddle);
        MenuBar menuBar = new MenuBar();
        setMenuBarData(menuBar);
        verticalLayoutLeft.add(menuBar);


        Button avatar = new Button(securityService.getAuthenticatedUser().getUsername());

        avatar.addClickListener(event -> {
            avatar.getUI().ifPresent(ui -> ui.getPage().setLocation(String.format("users/edit/%d", securityService.getAuthenticatedUser().getId())));
        });
        Button buttonLogOut = new Button("Odhlásit");
        buttonLogOut.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        buttonLogOut.addClickListener(event -> {
            securityService.logout();
        });
        HorizontalLayout horizontalLayoutAvatar = new HorizontalLayout(avatar, buttonLogOut);
        verticalLayoutRight.add(horizontalLayoutAvatar);

        horizontalLayoutMain.add(verticalLayoutLeft, verticalLayoutMiddle, verticalLayoutRight);
        addToNavbar(horizontalLayoutMain);
    }

    private void setMenuBarData(MenuBar menuBar) {
        menuBar.addItem("Domů", event -> menuBar.getUI().ifPresent(ui -> ui.navigate(Homepage.class)));
        menuBar.addItem("Auta", event -> menuBar.getUI().ifPresent(ui -> ui.navigate(CarsList.class)));
        menuBar.addItem("Vlastníci", event -> menuBar.getUI().ifPresent(ui -> ui.navigate(OwnerListDetail.class)));

        if (securityService.isAdmin() || securityService.isKrajOfficer()) {
            menuBar.addItem("Stanice", event -> menuBar.getUI().ifPresent(ui -> ui.navigate(OfficeListDetail.class)));
            menuBar.addItem("Uživatelé", event -> menuBar.getUI().ifPresent(ui -> ui.navigate(UserList.class)));

        }
    }
}
