package toolbox.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import toolbox.Toolbox;
import toolbox.common.items.ModItems;
import toolbox.common.materials.ModMaterials;
import toolbox.common.recipes.ModRecipes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import api.guide.BookChapter;
import api.guide.BookPage;
import api.guide.BookPageContents;
import api.guide.BookPageItemDisplay;
import api.guide.BookPageMat;
import api.guide.BookPageText;
import api.guide.BookPageTool;
import api.guide.ChapterLink;
import api.materials.AdornmentMaterial;
import api.materials.HaftMaterial;
import api.materials.HandleMaterial;
import api.materials.HeadMaterial;
import api.materials.Materials;

public class GuiBook extends GuiScreen {

	public static final int WIDTH = 146;
	public static final int HEIGHT = 180;
	private static final ResourceLocation BACKGROUND = new ResourceLocation(Toolbox.MODID, "textures/gui/book.png");

	public static void initPages() {
		BookPageContents home = new BookPageContents("guide.page.home.name");
		home.addLink(new ChapterLink("guide.chapter.misc.name", "misc"));
		home.addLink(new ChapterLink("guide.chapter.mat.name", "mat"));
		home.addLink(new ChapterLink("guide.chapter.tool.name", "tool"));
		home.addLink(new ChapterLink("guide.chapter.weapon.name", "weapon"));

		BookPageContents matHome = new BookPageContents("guide.page.mat.name");
		matHome.addLink(new ChapterLink("guide.chapter.head_mat.name", "head_mat"));
		matHome.addLink(new ChapterLink("guide.chapter.haft_mat.name", "haft_mat"));
		matHome.addLink(new ChapterLink("guide.chapter.handle_mat.name", "handle_mat"));
		matHome.addLink(new ChapterLink("guide.chapter.adornment_mat.name", "adornment_mat"));

		chapters.put("home", new BookChapter("home"));

		chapters.put("misc", new BookChapter("misc", "home"));
		chapters.put("mat", new BookChapter("mat", "home"));
		chapters.put("tool", new BookChapter("tool", "home"));
		chapters.put("weapon", new BookChapter("weapon", "home"));

		chapters.put("head_mat", new BookChapter("head_mat", "mat"));
		chapters.put("haft_mat", new BookChapter("haft_mat", "mat"));
		chapters.put("handle_mat", new BookChapter("handle_mat", "mat"));
		chapters.put("adornment_mat", new BookChapter("adornment_mat", "mat"));

		chapters.get("home").addPage(home);
		chapters.get("mat").addPage(matHome);

		chapters.get("misc").addPage(new BookPageText("guide.page.misc.name", "guide.page.misc.text"));

		for (HeadMaterial mat : Materials.head_registry.values()) {
			boolean hideUnavailables = true;
			// hideUnavailables = Config.HIDE_UNCRAFTABLE_HEADS;
			if (!hideUnavailables || (OreDictionary.getOres(mat.getCraftingItem()).size() > 0
					&& OreDictionary.getOres(mat.getSmallCraftingItem()).size() > 0)) {
				BookPageMat page = new BookPageMat(mat);
				chapters.get("head_mat").addPage(page);
			}
		}
		for (HaftMaterial mat : Materials.haft_registry.values()) {
			if (!ModRecipes.haft_map.containsValue(mat)) {
				continue;
			}
			BookPageMat page = new BookPageMat(mat);
			chapters.get("haft_mat").addPage(page);
		}
		for (HandleMaterial mat : Materials.handle_registry.values()) {
			if (!ModRecipes.handle_map.containsValue(mat)) {
				continue;
			}
			BookPageMat page = new BookPageMat(mat);
			chapters.get("handle_mat").addPage(page);
		}
		for (AdornmentMaterial mat : Materials.adornment_registry.values()) {
			if (mat == ModMaterials.ADORNMENT_NULL) {
				continue;
			}
			if (!ModRecipes.adornment_map.containsValue(mat)) {
				continue;
			}
			BookPageMat page = new BookPageMat(mat);
			chapters.get("adornment_mat").addPage(page);
		}

		chapters.get("tool").addPage(new BookPageTool("guide.tool.pickaxe.name", "guide.tool.pickaxe.desc", ModItems.pickaxe));
		chapters.get("tool").addPage(new BookPageTool("guide.tool.axe.name", "guide.tool.axe.desc", ModItems.axe));
		chapters.get("tool").addPage(new BookPageTool("guide.tool.shovel.name", "guide.tool.shovel.desc", ModItems.shovel));
		chapters.get("tool").addPage(new BookPageTool("guide.tool.hoe.name", "guide.tool.hoe.desc", ModItems.hoe));
		chapters.get("tool").addPage(new BookPageTool("guide.tool.handpick.name", "guide.tool.handpick.desc", ModItems.handpick));
		chapters.get("tool").addPage(new BookPageTool("guide.tool.hammer.name", "guide.tool.hammer.desc", ModItems.hammer));

		chapters.get("weapon")
				.addPage(new BookPageTool("guide.weapon.sword.name", "guide.weapon.sword.desc", ModItems.sword));
		chapters.get("weapon")
				.addPage(new BookPageTool("guide.weapon.dagger.name", "guide.weapon.dagger.desc", ModItems.dagger));
		chapters.get("weapon")
				.addPage(new BookPageTool("guide.weapon.mace.name", "guide.weapon.mace.desc", ModItems.mace));

		for (BookChapter chapter : chapters.values()) {
			if (chapter.getPageCount() <= 0) {
				chapter.addPage(new BookPage(chapter.getName()));
			}
		}
	}

	private static Map<String, BookChapter> chapters = new HashMap<>();

	private String currentChapter;
	private int currentPageNum;
	private BookPage currentPage;
	private String lastChapter;
	private int lastPageNum;

	private ItemStack display = ItemStack.EMPTY;

	private int left, top;

	public GuiBook() {
		currentChapter = "home";
		currentPageNum = 0;
	}

	@Override
	public void initGui() {
		this.top = (this.height - HEIGHT) / 2;
		this.left = (this.width - WIDTH) / 2;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().renderEngine.bindTexture(BACKGROUND);
		this.drawTexturedModalRect(left, top, 0, 0, WIDTH, HEIGHT);

		if (currentPage != null) {
			String header = TextFormatting.BOLD + "" + TextFormatting.UNDERLINE + I18n.format(currentPage.getTitle());
			int headerWidth = this.fontRenderer.getStringWidth(header);
			this.fontRenderer.drawString(header, left + (WIDTH - headerWidth) / 2, top + 12, 0);

			if (currentPage instanceof BookPageItemDisplay) {
				renderItemDisplayPage((BookPageItemDisplay) currentPage, mouseX, mouseY);
			} else if (currentPage instanceof BookPageMat) {
				renderMatPage((BookPageMat) currentPage, mouseX, mouseY);
			} else if (currentPage instanceof BookPageText) {
				renderTextPage((BookPageText) currentPage, mouseX, mouseY);
			}

			if (chapters.get(currentChapter).getPageCount() > 1) {
				GlStateManager.pushMatrix();
				float textScale = 0.5F;
				GlStateManager.scale(textScale, textScale, textScale);
				String pageNum = (currentPageNum + 1) + "/" + chapters.get(currentChapter).getPageCount();
				int pageNumWidth = (int) (this.fontRenderer.getStringWidth(pageNum) * textScale);
				int x = (int) ((left + (WIDTH - pageNumWidth) / 2) / textScale);
				this.fontRenderer.drawString(pageNum, x, (int) ((top + 164) / textScale), 0);
				GlStateManager.popMatrix();
			}
		}

		super.drawScreen(mouseX, mouseY, partialTicks);

	}

	private void renderItemDisplayPage(BookPageItemDisplay page, int mouseX, int mouseY) {

		ItemStack stack = page.getDisplayStack();

		if (stack.getMetadata() == 32767) {
			stack = new ItemStack(stack.getItem(), 1, 0, stack.getTagCompound());
		}

		if (stack.getItem() != display.getItem() || stack.getMetadata() != display.getMetadata()) {
			display = stack;
		}

		GlStateManager.pushMatrix();
		GlStateManager.scale(2F, 2F, 2F);
		RenderHelper.enableGUIStandardItemLighting();
		int itemX = (left + (WIDTH - 32) / 2);
		int itemY = (top + 24);
		float itemScale = 2F;
		this.itemRender.renderItemAndEffectIntoGUI(display, (int) (itemX / itemScale), (int) (itemY / itemScale));
		RenderHelper.disableStandardItemLighting();
		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		float textScale = 0.5F;
		GlStateManager.scale(textScale, textScale, textScale);
		this.fontRenderer.drawSplitString(I18n.format(page.getDescription()), (int) ((left + 18) / textScale),
				(int) ((top + 58) / textScale), (int) ((WIDTH - (18 * 2)) / textScale), 0);
		GlStateManager.popMatrix();

		if (mouseX >= itemX && mouseY >= itemY && mouseX <= itemX + (16 * itemScale) && mouseY <= itemY + (16 * itemScale)) {
			GlStateManager.pushMatrix();
			float toolTipScale = 0.5F;
			GlStateManager.scale(toolTipScale, toolTipScale, toolTipScale);
			this.renderToolTip(display, (int) (mouseX / toolTipScale), (int) (mouseY / toolTipScale));
			RenderHelper.disableStandardItemLighting();
			GlStateManager.popMatrix();
		}

	}

	private void renderMatPage(BookPageMat page, int mouseX, int mouseY) {

		display = page.getDisplayStack();
		GlStateManager.pushMatrix();
		float itemScale = 2F;
		int itemX = left + (WIDTH - 32) / 2;
		int itemY = top + 24;
		GlStateManager.scale(itemScale, itemScale, itemScale);
		RenderHelper.enableGUIStandardItemLighting();
		this.itemRender.renderItemAndEffectIntoGUI(display, (int) (itemX / itemScale), (int) (itemY / itemScale));
		RenderHelper.disableStandardItemLighting();
		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		float textScale = 0.5F;
		GlStateManager.scale(textScale, textScale, textScale);

		int i = 58;
		int increment = (int) (2 + fontRenderer.FONT_HEIGHT * textScale);
		if (page.getMat() instanceof HeadMaterial) {
			HeadMaterial mat = (HeadMaterial) page.getMat();
			this.fontRenderer.drawString(
					TextFormatting.UNDERLINE + I18n.format("guide.mat.stat.harvest_level.name") + TextFormatting.RESET
					+ ": " + mat.getHarvestLevel(),
					(int) ((left + 18) / textScale), (int) ((top + i) / textScale), 0);
			i += increment;
			this.fontRenderer.drawString(
					TextFormatting.UNDERLINE + I18n.format("guide.mat.stat.efficiency.name") + TextFormatting.RESET
					+ ": " + mat.getEfficiency(),
					(int) ((left + 18) / textScale), (int) ((top + i) / textScale), 0);
			i += increment;
			this.fontRenderer.drawString(
					TextFormatting.UNDERLINE + I18n.format("guide.mat.stat.durability.name") + TextFormatting.RESET
					+ ": " + mat.getDurability(),
					(int) ((left + 18) / textScale), (int) ((top + i) / textScale), 0);
			i += increment;
			this.fontRenderer.drawString(
					TextFormatting.UNDERLINE + I18n.format("guide.mat.stat.attack_damage.name") + TextFormatting.RESET
					+ ": " + mat.getAttackDamage(),
					(int) ((left + 18) / textScale), (int) ((top + i) / textScale), 0);
			i += increment;
			this.fontRenderer.drawString(
					TextFormatting.UNDERLINE + I18n.format("guide.mat.stat.enchantability.name") + TextFormatting.RESET
					+ ": " + mat.getEnchantability(),
					(int) ((left + 18) / textScale), (int) ((top + i) / textScale), 0);
			i += increment;
		} else if (page.getMat() instanceof HaftMaterial) {
			HaftMaterial mat = (HaftMaterial) page.getMat();
			this.fontRenderer.drawString(
					TextFormatting.UNDERLINE + I18n.format("guide.mat.stat.harvest_level_mod.name")
					+ TextFormatting.RESET + ": " + mat.getHarvestLevelMod(),
					(int) ((left + 18) / textScale), (int) ((top + i) / textScale), 0);
			i += increment;
			this.fontRenderer.drawString(
					TextFormatting.UNDERLINE + I18n.format("guide.mat.stat.efficiency_mod.name") + TextFormatting.RESET
					+ ": " + mat.getEfficiencyMod(),
					(int) ((left + 18) / textScale), (int) ((top + i) / textScale), 0);
			i += increment;
			this.fontRenderer.drawString(
					TextFormatting.UNDERLINE + I18n.format("guide.mat.stat.durability_mod.name") + TextFormatting.RESET
					+ ": " + mat.getDurabilityMod(),
					(int) ((left + 18) / textScale), (int) ((top + i) / textScale), 0);
			i += increment;
			this.fontRenderer.drawString(
					TextFormatting.UNDERLINE + I18n.format("guide.mat.stat.attack_damage_mod.name")
					+ TextFormatting.RESET + ": " + mat.getAttackDamageMod(),
					(int) ((left + 18) / textScale), (int) ((top + i) / textScale), 0);
			i += increment;
			this.fontRenderer.drawString(
					TextFormatting.UNDERLINE + I18n.format("guide.mat.stat.enchantability_mod.name")
					+ TextFormatting.RESET + ": " + mat.getEnchantabilityMod(),
					(int) ((left + 18) / textScale), (int) ((top + i) / textScale), 0);
			i += increment;
		} else if (page.getMat() instanceof HandleMaterial) {
			HandleMaterial mat = (HandleMaterial) page.getMat();
			this.fontRenderer.drawString(
					TextFormatting.UNDERLINE + I18n.format("guide.mat.stat.efficiency_mod.name") + TextFormatting.RESET
					+ ": " + mat.getEfficiencyMod(),
					(int) ((left + 18) / textScale), (int) ((top + i) / textScale), 0);
			i += increment;
			this.fontRenderer.drawString(
					TextFormatting.UNDERLINE + I18n.format("guide.mat.stat.durability_mod.name") + TextFormatting.RESET
					+ ": " + mat.getDurabilityMod(),
					(int) ((left + 18) / textScale), (int) ((top + i) / textScale), 0);
			i += increment;
			this.fontRenderer.drawString(
					TextFormatting.UNDERLINE + I18n.format("guide.mat.stat.enchantability_mod.name")
					+ TextFormatting.RESET + ": " + mat.getEnchantabilityMod(),
					(int) ((left + 18) / textScale), (int) ((top + i) / textScale), 0);
			i += increment;
		} else if (page.getMat() instanceof AdornmentMaterial) {
			AdornmentMaterial mat = (AdornmentMaterial) page.getMat();
			this.fontRenderer.drawString(
					TextFormatting.UNDERLINE + I18n.format("guide.mat.stat.harvest_level_mod.name")
					+ TextFormatting.RESET + ": " + mat.getHarvestLevelMod(),
					(int) ((left + 18) / textScale), (int) ((top + i) / textScale), 0);
			i += increment;
			this.fontRenderer.drawString(
					TextFormatting.UNDERLINE + I18n.format("guide.mat.stat.efficiency_mod.name") + TextFormatting.RESET
					+ ": " + mat.getEfficiencyMod(),
					(int) ((left + 18) / textScale), (int) ((top + i) / textScale), 0);
			i += increment;
			this.fontRenderer.drawString(
					TextFormatting.UNDERLINE + I18n.format("guide.mat.stat.durability_mod.name") + TextFormatting.RESET
					+ ": " + mat.getDurabilityMod(),
					(int) ((left + 18) / textScale), (int) ((top + i) / textScale), 0);
			i += increment;
			this.fontRenderer.drawString(
					TextFormatting.UNDERLINE + I18n.format("guide.mat.stat.attack_damage_mod.name")
					+ TextFormatting.RESET + ": " + mat.getAttackDamageMod(),
					(int) ((left + 18) / textScale), (int) ((top + i) / textScale), 0);
			i += increment;
			this.fontRenderer.drawString(
					TextFormatting.UNDERLINE + I18n.format("guide.mat.stat.enchantability_mod.name")
					+ TextFormatting.RESET + ": " + mat.getEnchantabilityMod(),
					(int) ((left + 18) / textScale), (int) ((top + i) / textScale), 0);
			i += increment;
		}

		if (!I18n.format(page.getDescription()).equals(page.getDescription())) {

			i += 6;
			this.fontRenderer.drawSplitString(TextFormatting.DARK_RED + I18n.format(page.getDescription()), (int) ((left + 18) / textScale),
					(int) ((top + i) / textScale), (int) ((WIDTH - (18 * 2)) / textScale), 0);

		}

		GlStateManager.popMatrix();

		if (mouseX >= itemX && mouseY >= itemY && mouseX <= itemX + (16 * itemScale) && mouseY <= itemY + (16 * itemScale)) {
			GlStateManager.pushMatrix();
			float toolTipScale = 0.5F;
			GlStateManager.scale(toolTipScale, toolTipScale, toolTipScale);
			this.renderToolTip(display, (int) (mouseX / toolTipScale), (int) (mouseY / toolTipScale));
			RenderHelper.disableStandardItemLighting();
			GlStateManager.popMatrix();
		}

	}

	private void renderTextPage(BookPageText page, int mouseX, int mouseY) {

		GlStateManager.pushMatrix();
		float textScale = 0.5F;
		GlStateManager.scale(textScale, textScale, textScale);
		String text = I18n.format(page.getText());
		List<String> paragraphs = new ArrayList<>();

		while (text.indexOf("|") > -1) {
			int i = text.indexOf("|");
			paragraphs.add("    " + text.substring(0, i));
			if (i < text.length() - 1) {
				text = text.substring(i + 1);
			}
		}
		paragraphs.add("    " + text);

		int i = 24;
		for (String par : paragraphs) {
			this.fontRenderer.drawSplitString(par, (int) ((left + 18) / textScale),
					(int) ((top + i) / textScale), (int) ((WIDTH - (18 * 2)) / textScale), 0);
			i += (int) (2 + fontRenderer.getWordWrappedHeight(par, (int) ((WIDTH - (18 * 2)) / textScale)) * textScale);
		}
		GlStateManager.popMatrix();
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		currentPage = chapters.get(currentChapter).getPage(currentPageNum);
		if (currentPageNum != lastPageNum || !currentChapter.equals(lastChapter)) {
			resetPage();
		}

		this.lastChapter = this.currentChapter;
		this.lastPageNum = this.currentPageNum;
	}

	private void resetPage() {
		this.buttonList.clear();
		int i = 0;
		if (currentPage instanceof BookPageContents) {
			List<ChapterLink> links = ((BookPageContents) currentPage).getLinks();
			for (ChapterLink link : links) {
				this.addButton(new ChapterLinkButton(i, left + 16, top + 24 + (i * 12), link.text, link.chapter));
				i++;
			}
		}
		if (currentPageNum < chapters.get(currentChapter).getPageCount() - 1) {
			this.addButton(new PageTurnButton(i, left + 100, top + 154, true));
			i++;
		}
		if (currentPageNum > 0) {
			this.addButton(new PageTurnButton(i, left + 18, top + 154, false));
			i++;
		}
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button instanceof ChapterLinkButton) {
			currentChapter = ((ChapterLinkButton) button).getChapter();
			currentPageNum = 0;
		} else if (button instanceof PageTurnButton) {
			if (((PageTurnButton) button).isForward()) {
				currentPageNum++;
			} else {
				currentPageNum--;
			}
		}
	}

	@Override
	protected void keyTyped(char par1, int par2) throws IOException {
		if (mc.gameSettings.keyBindInventory.getKeyCode() == par2) {
			if (currentChapter.equals(chapters.get(currentChapter).getParent())) {
				mc.displayGuiScreen(null);
				mc.setIngameFocus();
			} else {
				currentChapter = chapters.get(currentChapter).getParent();
				currentPageNum = 0;
			}
		} else if (1 == par2) {
			mc.displayGuiScreen(null);
			mc.setIngameFocus();
		}
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		if(mouseButton == 1) {
			currentChapter = chapters.get(currentChapter).getParent();
			currentPageNum = 0;
		}
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	public void onResize(Minecraft mcIn, int w, int h) {
		this.setWorldAndResolution(mcIn, w, h);
		this.resetPage();
	}

	@SideOnly(Side.CLIENT)
	public class ChapterLinkButton extends GuiButton {

		private String chapter;

		public ChapterLinkButton(int buttonId, int x, int y, String buttonText, String chapter) {
			super(buttonId, x, y, Minecraft.getMinecraft().fontRenderer.getStringWidth(I18n.format(buttonText)),
					Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT, buttonText);
			this.chapter = chapter;
		}

		@Override
		public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
			if (this.visible) {
				FontRenderer fontrenderer = mc.fontRenderer;
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				this.hovered = mouseX >= this.x && mouseY >= this.y
						&& mouseX < this.x + this.width && mouseY < this.y + this.height;
				int i = this.getHoverState(this.hovered);
				this.mouseDragged(mc, mouseX, mouseY);
				int j = 0;
				String p = "";

				if (!this.enabled) {
					j = 10526880;
				} else if (this.hovered) {
					j = 1450415;
					p += TextFormatting.UNDERLINE;
				}

				fontrenderer.drawString(p + I18n.format(this.displayString), this.x, this.y, j);
			}
		}

		public String getChapter() {
			return this.chapter;
		}

	}

	@SideOnly(Side.CLIENT)
	public class PageTurnButton extends GuiButton {

		private final boolean isForward;

		public PageTurnButton(int buttonId, int x, int y, boolean isForward) {
			super(buttonId, x, y, 23, 13, "");
			this.isForward = isForward;
		}

		@Override
		public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
			if (this.visible) {
				boolean flag = mouseX >= this.x && mouseY >= this.y
						&& mouseX < this.x + this.width && mouseY < this.y + this.height;
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				mc.getTextureManager().bindTexture(GuiBook.BACKGROUND);
				int i = 0;
				int j = 192;

				if (flag) {
					i += 23;
				}

				if (!this.isForward) {
					j += 13;
				}

				this.drawTexturedModalRect(this.x, this.y, i, j, 23, 13);
			}
		}

		public boolean isForward() {
			return this.isForward;
		}

	}

}
