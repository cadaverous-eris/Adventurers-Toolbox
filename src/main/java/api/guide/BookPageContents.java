package api.guide;

import java.util.ArrayList;
import java.util.List;

public class BookPageContents extends BookPage {

	private List<ChapterLink> links;
	
	public BookPageContents(String title) {
		super(title);
		this.links = new ArrayList<ChapterLink>();
	}
	
	public List<ChapterLink> getLinks() {
		return links;
	}
	
	public void addLink(ChapterLink link) {
		links.add(link);
	}

}
