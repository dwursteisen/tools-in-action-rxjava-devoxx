package com.github.devoxx.sandbox.tooling;

import static java.lang.String.format;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import com.github.devoxx.sandbox.model.Page;

public class CSV {

    public Path location;
    private Writer writer;

    public CSV() {
        try {
            this.location = Files.createTempFile("devoxxfr-2015-tia-rxjava", null);
        } catch (IOException ioe) {
            throw reportIOError(ioe);
        }
    }

    public CSV open() {
        try {
            writer = Files.newBufferedWriter(location, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
            return this;
        } catch (IOException ioe) {
            throw reportIOError(ioe);
        }
    }

    public void append(Page page) {
        try {
            Tools.threadInfo("append", page);
            checkWriter().append(format(
                    "%s,%s,%s,%s%n",
                    page.movie.id,
                    page.movie.title,
                    page.synopsis.posterUrl,
                    page.actors.size()));
        } catch (IOException ioe) {
            throw reportIOError(ioe);
        }
    }

    public void close() {
        try {
            Tools.threadInfo("close", this);
            checkWriter().close();
        } catch (IOException ioe) {
            throw reportIOError(ioe);
        }
    }

    private RuntimeException reportIOError(IOException ioe) {
        throw new RuntimeException(ioe);
    }

    private Writer checkWriter() {
        if (writer == null) {
            throw new IllegalStateException("CSV must be opened first => new CSV().open()");
        }
        return writer;
    }

    @Override
    public String toString() {
        return "Temp file located here : " + location;
    }
}
