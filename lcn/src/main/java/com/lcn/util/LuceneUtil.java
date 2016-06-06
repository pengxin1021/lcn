package com.lcn.util;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.TextFragment;
import org.apache.lucene.search.highlight.TokenSources;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.chenlb.mmseg4j.analysis.MMSegAnalyzer;
import com.lcn.entity.ShareResource;

public class LuceneUtil {

	private static String indexPath = "E:/luceneIndex1";
	private static int pageSize = 10;
	public static void main(String[] args) {
		ShareResource shareResource = new ShareResource();
		shareResource.setResourceId(333L);
		shareResource.setResourceName("就符合法律框架就离开哪里看的");
		shareResource.setResourceUrl("http://www.baidu.com");
		shareResource.setResourceSort(6);
		creatIndex(shareResource);
		search("resourceName", 6, "离开", 1);
	}

	public static void creatIndex(ShareResource shareResource) {
		try {
			System.out.println("创建索引路径'" + indexPath + "'...");
			Directory dir = FSDirectory.open(Paths.get(indexPath));
			// 创建解析器
			Analyzer analyzer = getAnalyzer("SmartChinese");
			IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
			IndexWriter writer = new IndexWriter(dir, iwc);
			//写入索引
			Document doc = new Document();
			doc.add(new LongField("resourceId", shareResource.getResourceId(), Field.Store.YES));
			doc.add(new TextField("resourceName",shareResource.getResourceName(),Field.Store.YES));
			doc.add(new StringField("resourceSort", String.valueOf(shareResource.getResourceSort()), Field.Store.YES));
			doc.add(new StringField("resourceUrl",shareResource.getResourceUrl(),Field.Store.YES));
			writer.addDocument(doc);
			writer.close();

		} catch (IOException e) {
			System.out.println(" caught a " + e.getClass() + "\n with message: " + e.getMessage());
		}
	}
	
	@SuppressWarnings("deprecation")
	public static List<ShareResource> search(String field, int sort, String keyWord, int page){
		List<ShareResource> shareResourceList = new ArrayList<ShareResource>();
		try {
			Directory directory = FSDirectory.open(Paths.get(indexPath));
			Analyzer analyzer = getAnalyzer("SmartChinese");
			IndexReader ireader = DirectoryReader.open(directory);
			IndexSearcher isearcher = new IndexSearcher(ireader);
			
			QueryParser qp1 = new QueryParser(field,  analyzer);
			qp1.setDefaultOperator(QueryParser.AND_OPERATOR);
			Query query1 = qp1.parse(keyWord);
			BooleanQuery booleanQuery = new BooleanQuery();
			booleanQuery.add(query1, BooleanClause.Occur.MUST);
			if(sort != 0){
				QueryParser qp2 = new QueryParser("resourceSort",  analyzer);
				qp2.setDefaultOperator(QueryParser.AND_OPERATOR);
				Query query2 = qp2.parse(String.valueOf(sort));
				booleanQuery.add(query2, BooleanClause.Occur.MUST);
			}
			System.out.println("Query = " + query1);
			
			hightLighter(isearcher, booleanQuery, analyzer, shareResourceList, page);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return shareResourceList;
	}
	
	/**
	 * 高亮显示
	 */
	@SuppressWarnings("deprecation")
	public static void hightLighter(IndexSearcher searcher, Query query, Analyzer analyzer, List<ShareResource> shareResourceList, int page){
		SimpleHTMLFormatter htmlFormatter = new SimpleHTMLFormatter("<font color='red'>", "</font>");
		Highlighter highlighter = new Highlighter(htmlFormatter, new QueryScorer(query));
		try {
			TopScoreDocCollector tsdc = TopScoreDocCollector.create(1000);
			searcher.search(query, tsdc);
			int start = pageSize * (page - 1);
			TopDocs topDocs = tsdc.topDocs(start, pageSize);
			if (topDocs.totalHits == 0) {
				return;
			}
			ScoreDoc[] hits = searcher.search(query, topDocs.totalHits).scoreDocs;
			for (int i = 0; i < topDocs.totalHits; i++){
				Document doc = searcher.doc(hits[i].doc);
				System.out.println("内容：" + doc.toString());
				TokenStream tokenStream = TokenSources.getAnyTokenStream(searcher.getIndexReader(), hits[i].doc, "resourceName", analyzer);
				TextFragment[] frag = highlighter.getBestTextFragments(tokenStream, doc.get("resourceName"), false, 10);
				ShareResource shareResource = new ShareResource();
				if (doc.get("resourceId") != null) {
					System.out.println(doc.get("resourceId"));
					shareResource.setResourceId(Long.parseLong(doc.get("resourceId")));
				}
				System.out.println(frag[0].toString());
				System.out.println(doc.get("resourceUrl"));
				shareResource.setResourceName(frag[0].toString());
				shareResource.setResourceSort(Integer.parseInt(doc.get("resourceSort")));
				shareResource.setResourceUrl(doc.get("resourceUrl"));
				shareResourceList.add(shareResource);
			}
		} catch (IOException | InvalidTokenOffsetsException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 分词器
	 * @return Analyzer
	 */
	private static Analyzer getAnalyzer(String analyzerType){
		Analyzer analyzer = null;
		switch (analyzerType) {
		case "SmartChinese":
			analyzer = new SmartChineseAnalyzer();
			break;
		case "Standard":
			analyzer = new StandardAnalyzer();
			break;
		case "MM":
			analyzer = new MMSegAnalyzer();
			break;
		default:
			analyzer = new SmartChineseAnalyzer();
			break;
		}
		return analyzer;
	}
}
