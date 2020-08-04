package com.blu.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.Renderer;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TableBlock;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension;
import org.commonmark.node.Link;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.AttributeProviderContext;
import org.commonmark.renderer.html.AttributeProviderFactory;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.beans.factory.annotation.CustomAutowireConfigurer;

public class MarkdownUtils  {

	/**
	 * markdown格式转HTML
	 * @param markdown
	 * @return
	 */
	public static String markdownToHtml(String markdown) {
		Parser parser = Parser.builder().build();
		Node document = parser.parse(markdown);
		HtmlRenderer renderer = HtmlRenderer.builder().build();
		return renderer.render(document);
	}
	
	/**
	 * markdown格式转HTML(增加扩展：标题锚点，表格生成)
	 * @param markdown
	 * @return
	 */
	public static String markdownToHtmlExtensions(String markdown) {
		//h标题生成id
		Set<Extension> headingAnchorExtensions = Collections.singleton(HeadingAnchorExtension.create());
		//转换table的HTML
		List<Extension> tablesExtension = Arrays.asList(TablesExtension.create());
		Parser parser = Parser.builder().extensions(tablesExtension).build();
		Node document = parser.parse(markdown);
		HtmlRenderer renderer = HtmlRenderer.builder().extensions(headingAnchorExtensions)
				.extensions(tablesExtension)
				.attributeProviderFactory(new AttributeProviderFactory() {
					
					@Override
					public AttributeProvider create(AttributeProviderContext context) {
						return new CustomAttributeProvider();
					}
				}).build();
		return renderer.render(document);
	}
	
	/**
	 * 处理标签的属性
	 * @author BLU
	 *
	 */
	static class CustomAttributeProvider implements AttributeProvider {

		@Override
		public void setAttributes(Node node, String tagName, Map<String, String> attributes) {
			if(node instanceof Link) {
				attributes.put("target", "_blank");
			}
			if(node instanceof TableBlock) {
				attributes.put("class", "ui celled table");
			}
			
		}
		
	}
	
}
