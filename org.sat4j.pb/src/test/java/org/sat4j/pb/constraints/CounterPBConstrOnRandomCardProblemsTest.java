/*
 * Created on 17 mars 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.sat4j.pb.constraints;

import junit.framework.TestCase;

import org.sat4j.pb.IPBSolver;

/**
 * @author leberre
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class CounterPBConstrOnRandomCardProblemsTest extends
        AbstractPBRandomCardProblemsTest{

    /**
     * @param arg0
     */
    public CounterPBConstrOnRandomCardProblemsTest(String arg0) {
        super(arg0);
    }

    /**
     * @see TestCase#setUp()
     */
    @Override
    protected IPBSolver createSolver() {
        return org.sat4j.pb.SolverFactory.newPBResAllPB();
    }

}