/*******************************************************************************
 * SAT4J: a SATisfiability library for Java Copyright (C) 2004-2008 Daniel Le Berre
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Alternatively, the contents of this file may be used under the terms of
 * either the GNU Lesser General Public License Version 2.1 or later (the
 * "LGPL"), in which case the provisions of the LGPL are applicable instead
 * of those above. If you wish to allow use of your version of this file only
 * under the terms of the LGPL, and not to allow others to use your version of
 * this file under the terms of the EPL, indicate your decision by deleting
 * the provisions above and replace them with the notice and other provisions
 * required by the LGPL. If you do not delete the provisions above, a recipient
 * may use your version of this file under the terms of the EPL or the LGPL.
 * 
 * Based on the pseudo boolean algorithms described in:
 * A fast pseudo-Boolean constraint solver Chai, D.; Kuehlmann, A.
 * Computer-Aided Design of Integrated Circuits and Systems, IEEE Transactions on
 * Volume 24, Issue 3, March 2005 Page(s): 305 - 317
 * 
 * and 
 * Heidi E. Dixon, 2004. Automating Pseudo-Boolean Inference within a DPLL 
 * Framework. Ph.D. Dissertation, University of Oregon.
 *******************************************************************************/
package org.sat4j.pb;

import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IOptimizationProblem;
import org.sat4j.specs.IVecInt;
import org.sat4j.specs.TimeoutException;

/**
 * Utility class to use optimization solvers instead of simple SAT solvers in
 * code meant for SAT solvers.
 * 
 * @author daniel
 */
public class OptToPBSATAdapter extends PBSolverDecorator {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    IOptimizationProblem problem;

    boolean modelComputed = false;

    public OptToPBSATAdapter(IOptimizationProblem problem) {
        super((IPBSolver) problem);
        this.problem = problem;
    }

    public boolean isSatisfiable() throws TimeoutException {
        modelComputed = false;
        return problem.admitABetterSolution();
    }

   public boolean isSatisfiable(boolean global) throws TimeoutException {
        throw new UnsupportedOperationException();
    }

    public boolean isSatisfiable(IVecInt assumps, boolean global)
            throws TimeoutException {
        throw new UnsupportedOperationException();
    }

    public boolean isSatisfiable(IVecInt assumps) throws TimeoutException {
        throw new UnsupportedOperationException();
    }

    public int[] model() {
    	if (modelComputed) 
    		return problem.model();
        try {
            assert problem.admitABetterSolution();
            if (problem.hasNoObjectiveFunction()) {
            	return problem.model();
            }
            do {
                problem.calculateObjective();
                problem.discard();
            } while (problem.admitABetterSolution());
        } catch (TimeoutException e) {
            // solver timeout
        } catch (ContradictionException e) {
            // OK, optimal model found
        }
        modelComputed = true;
        return problem.model();
    }

    public boolean model(int var) {
    	if (!modelComputed) model();
        return problem.model(var);
    }

    public String toString(String prefix) {        
        return prefix+"Optimization to Pseudo Boolean adapter\n"+super.toString(prefix);
    }
}