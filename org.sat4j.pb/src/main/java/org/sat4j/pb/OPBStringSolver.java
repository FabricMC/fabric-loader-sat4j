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
* Based on the original MiniSat specification from:
* 
* An extensible SAT solver. Niklas Een and Niklas Sorensson. Proceedings of the
* Sixth International Conference on Theory and Applications of Satisfiability
* Testing, LNCS 2919, pp 502-518, 2003.
*
* See www.minisat.se for the original solver in C++.
* 
*******************************************************************************/
package org.sat4j.pb;

import java.math.BigInteger;

import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IConstr;
import org.sat4j.specs.IVec;
import org.sat4j.specs.IVecInt;
import org.sat4j.specs.IteratorInt;
import org.sat4j.tools.DimacsStringSolver;

/**
 * Solver used to display in a string the pb-instance in OPB format.
 * 
 * That solver is useful to produce OPB files to be used by third party solvers.
 * 
 * @author parrain
 * 
 */
public class OPBStringSolver extends DimacsStringSolver implements IPBSolver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int indxConstrObj;
	
	private int nbOfConstraints;

	/**
	 * 
	 */
	public OPBStringSolver() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param initSize
	 */
	public OPBStringSolver(int initSize) {
		super(initSize);
		// TODO Auto-generated constructor stub
	}

	public IConstr addPseudoBoolean(IVecInt lits, IVec<BigInteger> coeffs,
			boolean moreThan, BigInteger d) throws ContradictionException {
		StringBuffer out = getOut();
		assert lits.size() == coeffs.size();
		nbOfConstraints ++;
		if (moreThan) {
			for (int i = 0; i <lits.size();i++)
				out.append(coeffs.get(i)+" x"+ lits.get(i) + " ");
        	out.append(">= "+d+" ;\n");
		}
        else {
			for (int i = 0; i <lits.size();i++)
				out.append(coeffs.get(i).negate()+" x"+ lits.get(i) + " ");
        	out.append(">= "+d.negate()+" ;\n");        	
        }
        return null;        
	}

	public void setObjectiveFunction(ObjectiveFunction obj) {
		StringBuffer out = getOut();
		StringBuffer tmp = new StringBuffer();
		tmp.append(" #constraint= "+nbOfConstraints+" \n");
		tmp.append("min : ");
		IVecInt lits = obj.getVars();
		IVec<BigInteger> coeffs = obj.getCoeffs();
		for (int i = 0; i <lits.size();i++)
			tmp.append(coeffs.get(i)+" x"+ lits.get(i) + " ");
		tmp.append(" ;\n");
		out.insert(indxConstrObj,tmp);
	}

	public IConstr addAtLeast(IVecInt literals, int degree)
			throws ContradictionException {
		StringBuffer out = getOut();
		nbOfConstraints++;
        for (IteratorInt iterator = literals.iterator();iterator.hasNext();)
            out.append("+1 x"+iterator.next() + " ");
        out.append(">= "+degree+" ;\n");
        return null;        
	}

	public IConstr addAtMost(IVecInt literals, int degree)
			throws ContradictionException {
		StringBuffer out = getOut();
		nbOfConstraints++;
        for (IteratorInt iterator = literals.iterator();iterator.hasNext();)
            out.append("-1 x"+iterator.next() + " ");
        out.append(">= "+(-degree)+" ;\n");
        return null;        
	}

	public IConstr addClause(IVecInt literals) throws ContradictionException {
		StringBuffer out = getOut();
		nbOfConstraints++;
        for (IteratorInt iterator = literals.iterator();iterator.hasNext();)
            out.append("+1 x"+iterator.next() + " ");
        out.append(">= 1 ;\n");
        return null;        
	}

	/* (non-Javadoc)
	 * @see org.sat4j.pb.IPBSolver#getExplanation()
	 */
	public String getExplanation() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.sat4j.pb.IPBSolver#setListOfVariablesForExplanation(org.sat4j.specs.IVecInt)
	 */
	public void setListOfVariablesForExplanation(IVecInt listOfVariables) {
		// TODO Auto-generated method stub

	}
	
	public String toString(){
		return getOut().toString();
	}

	public String toString(String prefix) {
        return "OPB output solver";
	}

    public int newVar(int howmany) {
		StringBuffer out = getOut();
        out.append("* #variable= " + howmany);
        setNbVars(howmany);
        // to add later the number of constraints
        indxConstrObj = out.length();
        return 0;
    }
    
    public void setExpectedNumberOfClauses(int nb) {
    }



}