package cz.upce.frontend.errorHandler;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;

import javax.annotation.security.PermitAll;

@Route("error")
@PermitAll
public class ErrorView extends VerticalLayout {

    public ErrorView() {
        HorizontalLayout layoutRow = new HorizontalLayout();
        VerticalLayout layoutColumn3 = new VerticalLayout();
        VerticalLayout layoutColumn2 = new VerticalLayout();
        H1 h1 = new H1();
        H3 h3 = new H3();
        VerticalLayout layoutColumn4 = new VerticalLayout();
        setHeightFull();
        setWidthFull();
        setFlexGrow(1.0, layoutRow);
        layoutRow.setWidthFull();
        layoutRow.addClassName(Gap.MEDIUM);
        layoutColumn3.setHeightFull();
        layoutColumn3.setWidth(null);
        layoutRow.setFlexGrow(1.0, layoutColumn2);
        layoutColumn2.setHeightFull();
        layoutColumn2.setWidth(null);
        h1.setText("Error");
        h3.setText("Požadovaná stránka nebyla nalezena nebo k ní nemáte přístup.");
        h1.getStyle().set("color", "red");
        h3.getStyle().set("color", "red");
        getStyle().set("background-color", "#ffffff");
        layoutColumn4.setHeightFull();
        layoutColumn4.setWidth(null);
        add(layoutRow);
        layoutRow.add(layoutColumn3);
        layoutRow.add(layoutColumn2);
        layoutColumn2.add(h1);
        layoutColumn2.add(h3);
        layoutRow.add(layoutColumn4);
    }
}
