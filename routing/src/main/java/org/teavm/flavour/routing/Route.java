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
package org.teavm.flavour.routing;

import java.util.function.Consumer;
import org.teavm.jso.browser.Location;
import org.teavm.jso.browser.Window;

/**
 *
 * @author Alexey Andreev
 */
public interface Route {
    default boolean parse() {
        return parse(Window.current());
    }

    default boolean parse(Window window) {
        Location location = window.getLocation();
        return parse(location.getPathName(), location.getSearch(), location.getHash());
    }

    default boolean parse(String pathName, String query, String hash) {
        StringBuilder sb = new StringBuilder();
        sb.append(pathName);
        if (query != null && !query.isEmpty()) {
            sb.append('?').append(query);
        }
        if (hash != null && !hash.isEmpty()) {
            sb.append('#').append(hash);
        }
        return parse(sb.toString());
    }

    default boolean parse(String path) {
        PathReader reader = Routing.getReader(this);
        if (reader == null) {
            throw new IllegalArgumentException("Wrong route interface: " + getClass().getName());
        }
        return reader.read(path, this);
    }

    static <T extends Route> T build(Class<T> routeType, Consumer<String> consumer) {
        return Routing.createBuilderProxy(routeType, consumer);
    }

    static <T extends Route> T open(Window window, Class<T> routeType) {
        return build(routeType, url -> {
            window.getLocation().assign(url);
        });
    }

    static <T extends Route> T open(Class<T> routeType) {
        return open(Window.current(), routeType);
    }
}