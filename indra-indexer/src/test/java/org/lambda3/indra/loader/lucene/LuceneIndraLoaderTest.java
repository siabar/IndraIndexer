package org.lambda3.indra.loader.lucene;

import org.apache.commons.math3.linear.RealVector;
import org.lambda3.indra.AnalyzedTerm;
import org.lambda3.indra.composition.SumVectorComposer;
import org.lambda3.indra.core.lucene.LuceneVectorSpace;
import org.lambda3.indra.indexer.builder.ModelBuilderTest;
import org.lambda3.indra.loader.RawSpaceModel;
import org.lambda3.indra.loader.SparseVector;
import org.lambda3.indra.loader.VectorIterator;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class LuceneIndraLoaderTest {

    @Test
    public void insertAndRead() {
        try {
            String baseDir = Files.createTempDirectory("indra-esa-test").toString();
            RawSpaceModel<SparseVector> esa = new ModelBuilderTest().createExplicitSemanticAnalysisBuilder();

            LuceneIndraLoader loader = new LuceneIndraLoader(baseDir, esa.modelMetadata);
            loader.load(esa);
            loader.close();

            String modelDir = Paths.get(baseDir, esa.modelMetadata.getConciseName()).toString();
            LuceneVectorSpace vs = new LuceneVectorSpace(modelDir);
            Assert.assertEquals(esa.modelMetadata, vs.getMetadata());

            VectorIterator<SparseVector> iter = esa.getVectorIterator();

            AtomicInteger counter = new AtomicInteger();
            while (iter.hasNext()) {
                counter.incrementAndGet();
                SparseVector sv = iter.next();
                AnalyzedTerm at = new AnalyzedTerm(sv.term, Collections.singletonList(sv.term));
                Map<String, RealVector> vectors = vs.getVectors(Collections.singletonList(at), new SumVectorComposer());

                RealVector vector = vectors.get(at.getTerm());
                Assert.assertEquals(sv.content, vector);
            }

            new File(baseDir).delete();
            System.out.println("number of vectors in esa " + counter.get());

        } catch (IOException e) {
            Assert.fail();
            e.printStackTrace();
        }
    }
}
