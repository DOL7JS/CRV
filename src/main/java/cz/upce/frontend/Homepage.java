package cz.upce.frontend;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Domovská stránka")
@Route(value = "",layout = Menu.class)
public class Homepage extends Composite<VerticalLayout> {

    public Homepage() {
        HorizontalLayout layoutRow = new HorizontalLayout();
        MenuBar menuBar = new MenuBar();
        VerticalLayout layoutColumn2 = new VerticalLayout();
        H1 h1 = new H1();
        getContent().setHeightFull();
        getContent().setWidthFull();
        layoutRow.setWidthFull();
        getContent().setFlexGrow(1.0, layoutColumn2);
        layoutColumn2.setWidthFull();
        h1.setText("Vítejte na stránkách CRV Suchdol");
        layoutColumn2.setFlexGrow(1.0, h1);
        layoutColumn2.setAlignSelf(FlexComponent.Alignment.CENTER, h1);
        getContent().add(layoutRow);
        layoutRow.add(menuBar);
        getContent().add(layoutColumn2);
        layoutColumn2.add(h1);
    }


}
