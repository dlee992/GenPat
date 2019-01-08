/**
 * Copyright (C) SEI, PKU, PRC. - All Rights Reserved.
 * Unauthorized copying of this file via any medium is
 * strictly prohibited Proprietary and Confidential.
 * Written by Jiajun Jiang<jiajun.jiang@pku.edu.cn>.
 */
package mfix.core.parse.node.expr;

import mfix.core.parse.match.metric.FVector;
import mfix.core.parse.node.Node;
import org.eclipse.jdt.core.dom.ASTNode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author: Jiajun
 * @date: 2018/9/21
 */
public class AryAcc extends Expr implements Serializable {

	private static final long serialVersionUID = 3197483700688117500L;
	private Expr _index = null;
	private Expr _array = null;

	/**
	 * ArrayAccess: Expression [ Expression ]
	 */
	public AryAcc(String fileName, int startLine, int endLine, ASTNode node) {
		super(fileName, startLine, endLine, node);
		_nodeType = TYPE.ARRACC;
	}

	public void setArray(Expr array) {
		_array = array;
	}

	public Expr getArray() {
		return _array;
	}

	public void setIndex(Expr index) {
		_index = index;
	}

	public Expr getIndex() {
		return _index;
	}

	@Override
	public List<Node> getAllChildren() {
		List<Node> children = new ArrayList<>(2);
		children.add(_array);
		children.add(_index);
		return children;
	}

	@Override
	public StringBuffer toSrcString() {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(_array.toSrcString());
		stringBuffer.append("[");
		stringBuffer.append(_index.toSrcString());
		stringBuffer.append("]");
		return stringBuffer;
	}
	
	@Override
	public void tokenize() {
		_tokens = new LinkedList<>();
		_tokens.addAll(_array.tokens());
		_tokens.add("[");
		_tokens.addAll(_index.tokens());
		_tokens.add("]");
	}

    @Override
	public boolean compare(Node other) {
		boolean match = false;
		if(other instanceof AryAcc) {
			match = _array.compare(((AryAcc) other)._array) && _index.compare(((AryAcc) other)._index);
		}
		return match;
	}
	
	@Override
	public void computeFeatureVector() {
		_fVector = new FVector();
		_fVector.inc(FVector.E_AACC);
		_fVector.inc(FVector.BRAKET_SQL);
		_fVector.inc(FVector.BRAKET_SQR);
		_fVector.combineFeature(_array.getFeatureVector());
		_fVector.combineFeature(_index.getFeatureVector());
	}
	
}
