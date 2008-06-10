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
package org.sat4j.pb.core;

import java.math.BigInteger;

import org.sat4j.minisat.core.AssertingClauseGenerator;
import org.sat4j.minisat.core.ILits;
import org.sat4j.minisat.core.IOrder;
import org.sat4j.minisat.core.LearningStrategy;
import org.sat4j.minisat.core.RestartStrategy;
import org.sat4j.minisat.core.SearchParams;
import org.sat4j.minisat.core.Solver;
import org.sat4j.pb.IPBSolver;
import org.sat4j.pb.ObjectiveFunction;
import org.sat4j.pb.orders.VarOrderHeapObjective;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IConstr;
import org.sat4j.specs.IVec;
import org.sat4j.specs.IVecInt;

public abstract class PBSolver<L extends ILits> extends
        Solver<L, PBDataStructureFactory<L>> implements IPBSolver {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public PBSolver(AssertingClauseGenerator acg,
            LearningStrategy<L, PBDataStructureFactory<L>> learner,
            PBDataStructureFactory<L> dsf, IOrder<L> order,
            RestartStrategy restarter) {
        super(acg, learner, dsf, order, restarter);
    }

    public PBSolver(AssertingClauseGenerator acg,
            LearningStrategy<L, PBDataStructureFactory<L>> learner,
            PBDataStructureFactory<L> dsf, SearchParams params,
            IOrder<L> order, RestartStrategy restarter) {
        super(acg, learner, dsf, params, order, restarter);
    }

    public IConstr addPseudoBoolean(IVecInt literals, IVec<BigInteger> coeffs,
            boolean moreThan, BigInteger degree) throws ContradictionException {
        IVecInt vlits = dimacs2internal(literals);
        assert vlits.size() == literals.size();
        assert literals.size() == coeffs.size();
        return addConstr(dsfactory.createPseudoBooleanConstraint(vlits, coeffs,
                moreThan, degree));
    }

    /**
     * list of variables for which the solver must provide an explanation for
     * the unsatisfiability if any
     */
    protected IVecInt listOfVariables;

    public void setListOfVariablesForExplanation(IVecInt lv) {
        listOfVariables = lv;
    }

    public String getExplanation() {
        return "";
    }

    public void setObjectiveFunction(ObjectiveFunction obj) {
        IOrder<L> order = getOrder();
        if (order instanceof VarOrderHeapObjective) {
            ((VarOrderHeapObjective) order).setObjectiveFunction(obj);
        }
    }
}