/**
 * Copyright (C) SEI, PKU, PRC. - All Rights Reserved.
 * Unauthorized copying of this file via any medium is
 * strictly prohibited Proprietary and Confidential.
 * Written by Jiajun Jiang<jiajun.jiang@pku.edu.cn>.
 */
package mfix.core.parse.node.stmt;

import mfix.core.parse.match.metric.FVector;
import mfix.core.parse.node.Node;
import mfix.core.parse.node.expr.ExprList;
import mfix.core.parse.node.expr.MType;
import org.eclipse.jdt.core.dom.ASTNode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author: Jiajun
 * @date: 2018/9/21
 */
public class ConstructorInv  extends Stmt implements Serializable {

	private static final long serialVersionUID = -680765569439500998L;
	private MType _thisType = null;
	private ExprList _arguments = null;
	
	/**
	 * ConstructorInvocation:
     *	[ < Type { , Type } > ]
     *	       this ( [ Expression { , Expression } ] ) ;
	 */
	public ConstructorInv(String fileName, int startLine, int endLine, ASTNode node) {
		this(fileName, startLine, endLine, node, null);
	}
	
	public ConstructorInv(String fileName, int startLine, int endLine, ASTNode node, Node parent) {
		super(fileName, startLine, endLine, node, parent);
		_nodeType = TYPE.CONSTRUCTORINV;
	}

	public String getClassStr() {
		return _thisType == null ? "DUMMY" : _thisType.toSrcString().toString();
	}

	public void setThisType(MType thisType){
		_thisType = thisType;
	}
	
	public void setArguments(ExprList arguments){
		_arguments = arguments;
	}

	public ExprList getArguments() {
		return _arguments;
	}

	@Override
	public StringBuffer toSrcString() {
		StringBuffer stringBuffer = new StringBuffer();
//		if(_thisType != null) {
//			stringBuffer.append(_thisType.toSrcString());
//			stringBuffer.append(".");
//		}
		stringBuffer.append("this(");
		stringBuffer.append(_arguments.toSrcString());
		stringBuffer.append(");");
		return stringBuffer;
	}

	@Override
	protected void tokenize() {
		_tokens = new LinkedList<>();
		_tokens.addAll(_arguments.tokens());
		_tokens.add(")");
		_tokens.add(";");
	}
	
	@Override
	public List<Node> getAllChildren() {
		List<Node> children = new ArrayList<>(1);
		children.add(_arguments);
		return children;
	}
	
	@Override
	public List<Stmt> getChildren() {
		return new ArrayList<>(0);
	}
	
	@Override
	public boolean compare(Node other) {
		boolean match = false;
		if(other instanceof ConstructorInv) {
			ConstructorInv constructorInv = (ConstructorInv) other;
			match = _arguments.compare(constructorInv._arguments);
		}
		return match;
	}
	
	@Override
	public void computeFeatureVector() {
		_fVector = new FVector();
		_fVector.inc(FVector.KEY_THIS);
		_fVector.combineFeature(_arguments.getFeatureVector());
	}

}
