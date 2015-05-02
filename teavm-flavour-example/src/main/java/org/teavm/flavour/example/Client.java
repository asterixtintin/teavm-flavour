/*
 *  Copyright 2015 Alexey Andreev.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.teavm.flavour.example;

import java.math.BigDecimal;
import java.util.Date;
import org.teavm.dom.browser.Window;
import org.teavm.dom.events.EventListener;
import org.teavm.dom.events.MouseEvent;
import org.teavm.dom.html.HTMLButtonElement;
import org.teavm.flavour.templates.Component;
import org.teavm.flavour.templates.Templates;
import org.teavm.jso.JS;

/**
 *
 * @author Alexey Andreev
 */
public final class Client {
    private Client() {
    }

    public static void main(String[] args) {
        Order order = new Order();
        order.setAddress("Townburgh, Elm street, 123");
        order.setReceiverName("John Doe");
        order.setDate(new Date());

        final OrderItem item = new OrderItem();
        item.setAmount(2);
        item.setSku("009876");
        item.setProductName("Rubber boots");
        item.setUnitPrice(new BigDecimal("30.00"));
        order.getItems().add(item);

        OrderItem item2 = new OrderItem();
        item2.setAmount(3);
        item2.setSku("005555");
        item2.setProductName("Plastic umbrella");
        item2.setUnitPrice(new BigDecimal("20.00"));
        order.getItems().add(item2);

        final Component component = Templates.bind(order, "application-content");

        final HTMLButtonElement button = (HTMLButtonElement)((Window)JS.getGlobal()).getDocument()
                .getElementById("refresh-button");
        button.addEventListener("click", new EventListener<MouseEvent>() {
            @Override
            public void handleEvent(MouseEvent evt) {
                item.setAmount(item.getAmount() + 1);
                component.render();
            }
        });
    }
}
