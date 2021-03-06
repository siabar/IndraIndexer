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
import java.io.File;
public class Corpus {

    public final CorpusMetadata metadata;
    private Iterator<Document> iter;
    private DocumentGenerator.ContentType type;
    private File file;
    Corpus(CorpusMetadata metadata, DocumentGenerator.ContentType type, File file) {
        this.metadata = metadata;
        this.type = type;
        this.file = file;
        reset();
    }

    public synchronized Iterator<Document> getDocumentsIterator() {
        return iter;
    }


    public synchronized void reset(){
        this.iter = new DocumentIterator(type, file);
    }
}
