/**
 * Copyright (C) 2014 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.dashbuilder.dataset.filter;

import java.util.List;
import java.util.ArrayList;

import org.dashbuilder.dataset.ColumnType;
import org.jboss.errai.common.client.api.annotations.Portable;

/**
 * Type of core filter functions available
 */
@Portable
public enum CoreFunctionType {

    IS_NULL(0),
    NOT_NULL(0),
    EQUALS_TO(1),
    NOT_EQUALS_TO(1),
    
    /**
     * <p>The <code>LIKE_TO</code> operator is intended to emulate the SQL like operator.It's used to search for a specified pattern in a data set's column.</p>
     * <p>Allowed wildcards are:</p>
     * <ul>
     *     <li><code>_</code> - A substitute for a single character.</li>
     *     <li><code>%</code> - A substitute for zero or more characters.</li>
     * </ul>
     * <p>The implementation considers case UNSENSITIVE.</p>
     */
    LIKE_TO(1),
    GREATER_THAN(1),
    GREATER_OR_EQUALS_TO(1),
    LOWER_THAN(1),
    LOWER_OR_EQUALS_TO(1),
    BETWEEN(2),
    TIME_FRAME(1);

    private final int parametersCount;

    private CoreFunctionType(int parametersCount) {
        this.parametersCount = parametersCount;
    }

    private static final CoreFunctionType[] coreFunctionTypes = values();

    public int getIndex() {
        return ordinal();
    }

    public int getParametersCount() {
        return parametersCount;
    }

    public boolean supportsType(ColumnType type) {
        if (TIME_FRAME.equals(this)) {
            return ColumnType.DATE.equals(type);
        }
        return true;
    }

    public static CoreFunctionType getByIndex(int index) {
        return coreFunctionTypes[index];
    }

    public static CoreFunctionType getByName(String type) {
        try {
            return valueOf(type.toUpperCase());
        } catch (Exception e) {
            return null;
        }
    }

    public static int getNumberOfParameters(String type) {
        CoreFunctionType ft = getByName(type);
        return ft.getParametersCount();
    }

    public static List<CoreFunctionType> getSupportedTypes(ColumnType columnType) {
        List<CoreFunctionType> result = new ArrayList<CoreFunctionType>();
        for (CoreFunctionType funType : coreFunctionTypes) {
            if (funType.supportsType(columnType)) {
                result.add(funType);
            }
        }
        return result;
    }
}
