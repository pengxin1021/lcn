package org.wltea.analyzer.lucene;

import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;

public final class MyAnalyzer extends Analyzer{
	
	private boolean isSmart;
	
	private Set stops;
	
	public boolean useSmart() {
		return isSmart;
	}

	public void setUseSmart(boolean useSmart) {
		this.isSmart = useSmart;
	}

	//默认细粒度切分算法
	public MyAnalyzer(){
		this(false);
	}
	
	//isSmart表示是否使用智能切分，true表示使用
	public MyAnalyzer(boolean isSmart){
		super();
		this.isSmart = isSmart;
	}

	//重载Analyzer接口，构造分词组件
	@Override
	protected TokenStreamComponents createComponents(String fieldName) {
		Tokenizer tokenizer = new MyTokenizer(this.useSmart());
		return new TokenStreamComponents(tokenizer);
	}
}
