package org.vaadin.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Type;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A sample Vaadin view class.
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and
 * use @Route annotation to announce it in a URL as a Spring managed
 * bean.
 * Use the @PWA annotation make the application installable on phones,
 * tablets and some desktop browsers.
 * <p>
 * A new instance of this class is created for every new user and every
 * browser tab/window.
 */
@Route
@PWA(name = "Vaadin Application",
        shortName = "Vaadin App",
        description = "This is an example Vaadin application.",
        enableInstallPrompt = false)
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class MainView extends VerticalLayout {

    final Gson gson = new Gson();

    IP ips;
    ArrayList<IP> ipsArray;

    ArrayList<IP> IPListMostrar = new ArrayList<>();

    Grid<IP> gridZonasBasicas = new Grid<>(IP.class); //Se crea un objeto Grid de la clase data

    Dialog dialog = new Dialog();

    /**
     * Construct a new Vaadin view.
     * <p>
     * Build the initial UI state for the user accessing the application.
     *
     * @param service The message service. Automatically injected Spring managed bean.
     */
    public MainView(@Autowired GreetService service) {

        Type listaZonasBasicas = new TypeToken<ArrayList<IP>>() { //Se crea un objeto Type para parsear el json
        }.getType();

        ipsArray = gson.fromJson(service.getIP("IP"), listaZonasBasicas);

        // POST
        //-----------------------------------------------------------------------------------------------

        H2 headline = new H2("Zona basica seleccionada (se necesita completar todos los campos)");
        headline.getStyle().set("margin", "var(--lumo-space-m) 0 0 0")
                .set("font-size", "1.5em").set("font-weight", "bold");

        FormLayout nameLayout = new FormLayout(); //Se crea un layout para el nombre de la zona básica

        //Se crean los campos
        TextField ip_fromField = new TextField("ip_from");
        TextField zonasaludField = new TextField("ip_to");
        TextField tiau14dField = new TextField("country_code");
        TextField tiatField = new TextField("country_name");
        TextField cctField = new TextField("region_name");
        TextField ccu14dField = new TextField("city_name");
        TextField fechaInformeField = new TextField("latitude");
        TextField longitudeField = new TextField("longitude");
        TextField zip_codField = new TextField("zip_code");
        TextField time_zoneField = new TextField("time_zone");

        nameLayout.add(ip_fromField, zonasaludField, tiau14dField, tiatField, cctField, ccu14dField, fechaInformeField,longitudeField, zip_codField, time_zoneField);

        nameLayout.setResponsiveSteps( //Se añaden los pasos responsivos
                new FormLayout.ResponsiveStep("1px", 1),
                new FormLayout.ResponsiveStep("600px", 2),
                new FormLayout.ResponsiveStep("700px", 3));

        Button buttonAñadir = new Button("Añadir");

        buttonAñadir.addClickListener(e -> {
            buttonAñadir.setEnabled(false);
            Type listaZonaBasica = new TypeToken<ArrayList<IP>>() {
            }.getType();
            ipsArray = gson.fromJson(service.postIP("IP", ip_fromField.getValue(), zonasaludField.getValue(), tiau14dField.getValue(), tiatField.getValue(), cctField.getValue(), ccu14dField.getValue(), fechaInformeField.getValue(), longitudeField.getValue(), zip_codField.getValue(), time_zoneField.getValue()), listaZonaBasica); //Se añade la zona básica a la lista
            Notification notification = Notification.show("IP añadida");
            notification.setPosition(Notification.Position.BOTTOM_END);
            notification.addDetachListener(detachEvent -> buttonAñadir.setEnabled(true));
        });

        VerticalLayout dialogLayout = new VerticalLayout(headline, nameLayout, buttonAñadir);
        dialogLayout.setPadding(false);
        dialogLayout.setAlignItems(Alignment.STRETCH);
        dialogLayout.getStyle().set("min-width", "900px")
                .set("max-width", "100%").set("height", "80%");

        //------------------------------------------------------------------------------------------------------------------------
        // GET y GET ID
        //------------------------------------------------------------------------------------------------------------------------


        gridZonasBasicas.setItems(ipsArray);
        gridZonasBasicas.setColumns("id", "ip_from", "ip_to", "country_code", "country_name", "region_name", "city_name","latitude", "longitude", "zip_code", "time_zone");
        gridZonasBasicas.addItemClickListener( //Se añade un listener para cuando se haga click en una fila de la tabla
                event -> {
                    IPListMostrar.add(new IP(event.getItem().getId(), event.getItem().getIp_from(), event.getItem().getIp_to(), event.getItem().getCountry_code(), event.getItem().getCountry_name(), event.getItem().getRegion_name(), event.getItem().getCity_name(),  event.getItem().getLatitude(), event.getItem().getLongitude(), event.getItem().getZip_code(), event.getItem().getTime_zone()));
                    dialog.getElement().setAttribute("aria-label", "IP seleccionado");
                    VerticalLayout dialogLayout1 = createDialogLayout(IPListMostrar);
                    Button button = new Button("Cerrar");
                    button.addClickListener(e -> {

                        dialog.close();
                        dialog.removeAll();
                        IPListMostrar.clear();
                    });

                    dialog.add(dialogLayout1);
                    dialog.add(button);

                    dialog.setCloseOnEsc(false);
                    dialog.setCloseOnOutsideClick(false);
                    dialog.setDraggable(true);
                    dialog.setResizable(true);
                    dialog.open();

                });

        Button buttonActualizar = new Button("Actualizar Grid");
        buttonActualizar.addClickListener(e -> {
            gridZonasBasicas.setItems(ipsArray);
            dialog.close();
            dialog.removeAll();
            IPListMostrar.clear();
        });

        //------------------------------------------------------------------------------------------------------------------------
        // PUT
        //------------------------------------------------------------------------------------------------------------------------

        H2 headline1 = new H2("Zona basica seleccionada (se necesita completar todos los campos)");
        headline1.getStyle().set("margin", "var(--lumo-space-m) 0 0 0")
                .set("font-size", "1.5em").set("font-weight", "bold");

        FormLayout nameLayout1 = new FormLayout(); //Se crea un layout para el nombre de la zona básica

        //Se crean los campos
        TextField idField1 = new TextField("id");
        TextField ip_fromField1 = new TextField("ip_from");
        TextField zonasaludField1 = new TextField("ip_to");
        TextField tiau14dField1 = new TextField("country_code");
        TextField tiatField1 = new TextField("country_name");
        TextField cctField1 = new TextField("region_name");
        TextField ccu14dField1 = new TextField("city_name");
        TextField fechaInformeField1 = new TextField("latitude");
        TextField longitudeField1 = new TextField("longitude");
        TextField zip_codField1 = new TextField("zip_code");
        TextField time_zoneField1 = new TextField("time_zone");

        nameLayout1.add(idField1,ip_fromField1, zonasaludField1, tiau14dField1, tiatField1, cctField1, ccu14dField1, fechaInformeField1, longitudeField1, zip_codField1, time_zoneField1);

        nameLayout1.setResponsiveSteps( //Se añaden los pasos responsivos
                new FormLayout.ResponsiveStep("1px", 1),
                new FormLayout.ResponsiveStep("600px", 2),
                new FormLayout.ResponsiveStep("700px", 3));

        //Se crea un layout para los botones
        Button buttonEditar = new Button("Editar");
        buttonEditar.addClickListener(e -> {
            buttonEditar.setEnabled(false);
            for (int i = 0; i < ipsArray.size(); i++){
                if ((ipsArray.get(i).getId()) == Integer.parseInt(idField1.getValue())){
                    Type listaZonaBasica = new TypeToken<ArrayList<IP>>() {}.getType();
                    ipsArray = gson.fromJson(service.putIP("IP", Integer.parseInt(idField1.getValue()) ,ip_fromField1.getValue(), zonasaludField1.getValue(), tiau14dField1.getValue(), tiatField1.getValue(), cctField1.getValue(), ccu14dField1.getValue(), fechaInformeField1.getValue(), longitudeField1.getValue(), zip_codField1.getValue(), time_zoneField1.getValue()), listaZonaBasica);
                    Notification notification = Notification.show("IP editada");
                    notification.setPosition(Notification.Position.BOTTOM_END);
                    notification.addDetachListener(detachEvent -> buttonEditar.setEnabled(true));
                }
            }

        });
        VerticalLayout dialogLayout1 = new VerticalLayout(headline1, nameLayout1, buttonEditar);
        dialogLayout1.setPadding(false);
        dialogLayout1.setAlignItems(Alignment.STRETCH);
        dialogLayout1.getStyle().set("min-width", "900px")
                .set("max-width", "100%").set("height", "80%");

        //-----------------------------------------------------------------------------------------------------------------------------------------
        // DELETE
        //-----------------------------------------------------------------------------------------------------------------------------------------

        H2 headline2 = new H2("Zona basica seleccionada (se necesita completar todos los campos)");
        headline1.getStyle().set("margin", "var(--lumo-space-m) 0 0 0")
                .set("font-size", "1.5em").set("font-weight", "bold");

        FormLayout nameLayout2 = new FormLayout(); //Se crea un layout para el nombre de la zona básica

        TextField idField2 = new TextField("id");

        nameLayout2.add(idField2);

        nameLayout2.setResponsiveSteps( //Se añaden los pasos responsivos
                new FormLayout.ResponsiveStep("1px", 1),
                new FormLayout.ResponsiveStep("600px", 2),
                new FormLayout.ResponsiveStep("700px", 3));

        //Se crea un layout para los botones
        Button buttonEliminar = new Button("Borrar");
        buttonEliminar.addClickListener(e -> {
            buttonEliminar.setEnabled(false);
            if (idField2.getValue() == ""){
                Notification notification = Notification.show("No se ha facilitado el ID");
                notification.setPosition(Notification.Position.BOTTOM_END);
                notification.addDetachListener(detachEvent -> buttonEliminar.setEnabled(true));
            }
            else {
                Type listaZonaBasica = new TypeToken<ArrayList<IP>>() {
                }.getType();
                ipsArray = (gson.fromJson(service.deleteIP("IP", Integer.parseInt(idField2.getValue())), listaZonaBasica));
                Notification notification = Notification.show("IP borrada");
                notification.setPosition(Notification.Position.BOTTOM_END);
                notification.addDetachListener(detachEvent -> buttonEliminar.setEnabled(true));
            }
        });
        VerticalLayout dialogLayout2 = new VerticalLayout(headline2, nameLayout2, buttonEliminar);
        dialogLayout2.setPadding(false);
        dialogLayout2.setAlignItems(Alignment.STRETCH);
        dialogLayout2.getStyle().set("min-width", "900px")
                .set("max-width", "100%").set("height", "80%");

        //----------------------------------------------------------------------------------------------------------------------

        //Se crean los campos

        Tab tabZonasBasicas = new Tab("POST");
        Div pageZonasBasicas = new Div();
        pageZonasBasicas.setWidth(1600, Unit.PIXELS);
        pageZonasBasicas.add(dialogLayout);

        Tab tabGET = new Tab("GET");
        Div pageGET = new Div();
        pageGET.setWidth(1600, Unit.PIXELS);
        pageGET.add(gridZonasBasicas, buttonActualizar);
        pageGET.setVisible(false);

        Tab tabPUT = new Tab("PUT");
        Div pagePUT = new Div();
        pagePUT.setWidth(1600, Unit.PIXELS);
        pagePUT.add(dialogLayout1);
        pagePUT.setVisible(false);

        Tab tabDELETE = new Tab("DELETE");
        Div pageDELETE = new Div();
        pageDELETE.setWidth(1600, Unit.PIXELS);
        pageDELETE.add(dialogLayout2);
        pageDELETE.setVisible(false);

        Map<Tab, Component> tabsToPages = new HashMap<>(); //Se crea un mapa para relacionar las pestañas con las páginas
        tabsToPages.put(tabZonasBasicas, pageZonasBasicas);
        tabsToPages.put(tabGET, pageGET);
        tabsToPages.put(tabPUT, pagePUT);
        tabsToPages.put(tabDELETE, pageDELETE);

        Tabs tabs = new Tabs(tabZonasBasicas, tabGET, tabPUT, tabDELETE); //Se añaden las pestañas al componente tabs

        tabs.addThemeVariants(TabsVariant.LUMO_EQUAL_WIDTH_TABS); //Se añade un estilo al componente tabs
        tabs.addSelectedChangeListener(evetnt -> { //Se añade un listener para cuando se cambie de pestaña
            tabsToPages.values().forEach(page -> page.setVisible(false));
            Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
            selectedPage.setVisible(true);
        });

        tabs.setSizeFull();

        add(tabs, pageZonasBasicas, pageGET, pagePUT, pageDELETE);

    }


    private static VerticalLayout createDialogLayout(ArrayList<IP> lista) {
        H2 headline = new H2("IP seleccionado");
        headline.getStyle().set("margin", "var(--lumo-space-m) 0 0 0")
                .set("font-size", "1.5em").set("font-weight", "bold");

        Grid<IP> grid = new Grid<>(IP.class, false);
        grid.addColumn(IP::getId).setHeader("Id");
        grid.addColumn(IP::getIp_from).setHeader("ip_from");
        grid.addColumn(IP::getIp_to).setHeader("ip_to");
        grid.addColumn(IP::getCountry_code).setHeader("country_code");
        grid.addColumn(IP::getCountry_name).setHeader("country_name");
        grid.addColumn(IP::getRegion_name).setHeader("region_name");
        grid.addColumn(IP::getCity_name).setHeader("city_name");
        grid.addColumn(IP::getLatitude).setHeader("latitude");
        grid.addColumn(IP::getLongitude).setHeader("longitude");
        grid.addColumn(IP::getZip_code).setHeader("zip_code");
        grid.addColumn(IP::getTime_zone).setHeader("time_zone");

        List<IP> equipo = lista;
        grid.setItems(equipo);
        grid.setColumns("id", "ip_from", "ip_to", "country_code", "country_name", "region_name", "city_name","latitude", "longitude", "zip_code", "time_zone");


        VerticalLayout dialogLayout = new VerticalLayout(headline, grid);
        dialogLayout.setPadding(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("min-width", "900px")
                .set("max-width", "100%").set("height", "80%");

        return dialogLayout;
    }


}
