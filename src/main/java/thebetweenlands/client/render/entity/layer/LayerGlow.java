package thebetweenlands.client.render.entity.layer;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

/**
 * Adds a simple glow layer to the renderer

 * @param <T>
 */
public class LayerGlow<T extends EntityLivingBase> implements LayerRenderer<T> {
	public final RenderLivingBase<T> renderer;
	public final ResourceLocation glowTexture;

	public LayerGlow(RenderLivingBase<T> renderer, ResourceLocation glowTexture) {
		this.renderer = renderer;
		this.glowTexture = glowTexture;
	}

	@Override
	public void doRenderLayer(T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.renderer.bindTexture(this.glowTexture);
		GlStateManager.enableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
		GlStateManager.depthMask(!entity.isInvisible());
		int i = 61680;
		int j = i % 65536;
		int k = i / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);
		GlStateManager.enableLighting();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		ModelBase mainModel = this.renderer.getMainModel();
		mainModel.setLivingAnimations(entity, limbSwingAmount, ageInTicks, partialTicks);
		mainModel.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entity);
		mainModel.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		this.setLightmap(entity, partialTicks);
		GlStateManager.depthMask(true);
		GlStateManager.disableBlend();
	}

	@Override
	public boolean shouldCombineTextures() {
		return false;
	}

	/**
	 * Updates the lighting map for the position of the specified entity
	 * @param entityLivingIn
	 * @param partialTicks
	 */
	protected void setLightmap(T entityLivingIn, float partialTicks) {
		int i = entityLivingIn.getBrightnessForRender(partialTicks);
		int j = i % 65536;
		int k = i / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);
	}
}
