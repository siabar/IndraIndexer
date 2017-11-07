package org.lambda3.indra.indexer;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.Parameters;
import org.lambda3.indra.corpus.Corpus;
import org.lambda3.indra.corpus.CorpusLoader;
import org.lambda3.indra.indexer.builder.*;

import java.io.File;
import java.io.IOException;


public class IndraIndexerCommandLine {

    private static final String INDEXER_NAME = "Indra Indexer v.%s";

    public static void main(String... args) {
        String version = IndraIndexerCommandLine.class.getPackage().getImplementationVersion();

        MainCommand main = new MainCommand();
        JCommander jc = new JCommander(main);
        jc.setProgramName(String.format(INDEXER_NAME, version));
        IndexerCommand indexCmd = new IndexerCommand();
        jc.addCommand("indexer", indexCmd);

        try {
            jc.parse(args);
        } catch (ParameterException e) {
            e.printStackTrace();
            jc.usage();
        }

        if (jc.getParsedCommand() == null) {
            jc.usage();
        }

        try {
            ModelBuilder builder;
            Corpus corpus = CorpusLoader.load(indexCmd.corpusDir);

            if (indexCmd.modelName.equalsIgnoreCase("ESA"))
                builder = new ExplicitSemanticAnalysisBuilder(indexCmd.output, indexCmd.minWordFrequency);
            else if (indexCmd.modelName.equalsIgnoreCase("LSA"))
                builder = new LatentSemanticAnalysisBuilder(indexCmd.output, indexCmd.numOfDimensions,
                        indexCmd.windowsSize, indexCmd.minWordFrequency);
            else if (indexCmd.modelName.equalsIgnoreCase("GLOVE"))
                builder = new GloveModelBuilder(indexCmd.output, indexCmd.numOfDimensions,
                        indexCmd.windowsSize, indexCmd.minWordFrequency);
            else if (indexCmd.modelName.equalsIgnoreCase("W2V"))
                builder = new Word2VecModelBuilder(indexCmd.output, indexCmd.numOfDimensions,
                        indexCmd.windowsSize, indexCmd.minWordFrequency);
            else
                throw new IllegalStateException(String.format("Model '%s' is not supported. Please, choose one " +
                        "of the following: ESA, LSA, GLOVE, W2V.", indexCmd.modelName));

            builder.build(corpus);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Common parameters for all commands.
     */
    @Parameters(commandDescription = "See commands below.")
    private static final class MainCommand {
        @Parameter(names = "--help", help = true, description = "You know this...")
        boolean help;
    }

    @Parameters(commandDescription = "Generate Models.", separators = "=")
    private static class IndexerCommand {
        @Parameter(names = {"-m", "--model"}, required = true, description = "Input name of the model.", order = 0)
        String modelName;

        @Parameter(names = {"-c", "--corpus-dir"}, required = true, description = "A directory in which there are two " +
                "files. The first is 'copus.metadata' containing the metadata information and the second is " +
                "'corpus.txt' contaning the data it self. 'corpus.metadata' file is generated automatically during the " +
                "preprocess step. In the case that your data was not preprocessed by Indra, please generate the metadata " +
                "file before starting the model generation.", order = 1)
        File corpusDir;

        @Parameter(names = {"-d", "--dimensions"}, required = true, description = "The number of dimensions.", order = 3)
        int numOfDimensions;

        @Parameter(names = {"-o", "--output"}, required = true, description = "The output directory.", order = 4)
        String output;

        @Parameter(names = {"-w", "--windows-size"}, description = "Window Size.", order = 10)
        int windowsSize = 5;

        @Parameter(names = {"-f", "--min-word-frequency"}, description = "Min word frequency.", order = 15)
        int minWordFrequency = 5;
    }
}
