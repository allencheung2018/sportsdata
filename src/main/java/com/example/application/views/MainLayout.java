package com.example.application.views;

import com.example.application.views.list.*;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class MainLayout extends AppLayout { 

    public MainLayout() {
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("Sports");
        logo.addClassNames(
            LumoUtility.FontSize.LARGE, 
            LumoUtility.Margin.MEDIUM);

        DrawerToggle toggle = new DrawerToggle();
        var header = new HorizontalLayout(toggle, logo );

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER); 
        header.setWidthFull();
        header.addClassNames(
            LumoUtility.Padding.Vertical.NONE,
            LumoUtility.Padding.Horizontal.MEDIUM);

        addToNavbar(header); 

    }

    private void createDrawer() {
        addToDrawer(new VerticalLayout( 
                new RouterLink("RecordAH", RecordAHLayout.class),
                new RouterLink("E0", ListViewE0.class),
                new RouterLink("E1", ListViewE1.class),
                new RouterLink("D1", ListViewD1.class),
                new RouterLink("D2", ListViewD2.class),
                new RouterLink("SP1", ListViewSP1.class),
                new RouterLink("SP2", ListViewSP2.class),
                new RouterLink("I1", ListViewI1.class),
                new RouterLink("I2", ListViewI2.class),
                new RouterLink("F1", ListViewF1.class),
                new RouterLink("F2", ListViewF2.class),
                new RouterLink("N1", ListViewN1.class),
                new RouterLink("P1", ListViewP1.class),
                new RouterLink("SWE", ListViewSWE.class)
        ));
    }
}