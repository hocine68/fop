/*
 * $Id$
 * Copyright (C) 2001 The Apache Software Foundation. All rights reserved.
 * For details on use and redistribution please refer to the
 * LICENSE file included with these sources.
 */

package org.apache.fop.pdf;

// Java
import java.io.PrintWriter;
import org.apache.fop.messaging.MessageHandler;
import java.util.Vector;

/**
 * class representing a /Pages object.
 *
 * A /Pages object is an ordered collection of pages (/Page objects)
 * (Actually, /Pages can contain further /Pages as well but this
 * implementation doesn't allow this)
 */
public class PDFPages extends PDFObject {

    /**
     * the /Page objects
     */
    protected Vector kids = new Vector();

    /**
     * the number of /Page objects
     */
    protected int count = 0;

    // private PDFPages parent;

    /**
     * create a /Pages object.
     *
     * @param number the object's number
     */
    public PDFPages(int number) {

        /* generic creation of object */
        super(number);
    }

    /**
     * add a /Page object.
     *
     * @param page the PDFPage to add.
     */
    public void addPage(PDFPage page) {
        this.kids.addElement(page);
        page.setParent(this);
        this.incrementCount();
    }

    /**
     * get the count of /Page objects
     *
     * @return the number of pages
     */
    public int getCount() {
        return this.count;
    }

    /**
     * increment the count of /Page objects
     */
    public void incrementCount() {
        this.count++;
        // MessageHandler.logln("Incrementing count to " + this.getCount());
    }

    /**
     * represent the object in PDF
     *
     * @return the PDF string
     */
    public byte[] toPDF() {
        StringBuffer p = new StringBuffer(this.number + " " + this.generation
                                          + " obj\n<< /Type /Pages\n/Count "
                                          + this.getCount() + "\n/Kids [");
        for (int i = 0; i < kids.size(); i++) {
            p = p.append(((PDFObject)kids.elementAt(i)).referencePDF() + " ");
        }
        p = p.append("] >>\nendobj\n");
        return p.toString().getBytes();
    }

}
