package org.lambda3.indra.corpus;

/*-
 * ==========================License-Start=============================
 * indra-preprocessing
 * --------------------------------------------------------------------
 * Copyright (C) 2017 Lambda^3
 * --------------------------------------------------------------------
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * ==========================License-End===============================
 */

import java.util.Iterator;
import java.util.Objects;

public class Document {
    public final int id;
    public final String content;

    public Document(int id, String content) {
        this.id = id;
        this.content = content;
    }

    public static Document simpleDocument(String content) {
        return new Document(0, content);
    }

    public static Document simpleDocument(Iterator<String> content) {
        Iterable<String> iterable = () -> content;
        return Document.simpleDocument(String.join(" ", iterable));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Document document = (Document) o;
        return id == document.id &&
                Objects.equals(content, document.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content);
    }

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }
}
