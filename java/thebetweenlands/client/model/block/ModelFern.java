// Date: 31/03/2015 17:50:35
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX






package thebetweenlands.client.model.block;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelFern extends ModelBase
{
  //fields
    ModelRenderer fern1;
    ModelRenderer fern2;
    ModelRenderer fern3;
    ModelRenderer fern4;
    ModelRenderer fern5;
    ModelRenderer fern6;
    ModelRenderer fern7;
    ModelRenderer fern8;
    ModelRenderer middle1;
    ModelRenderer middle2;
  
  public ModelFern()
  {
    textureWidth = 128;
    textureHeight = 64;
    
      fern1 = new ModelRenderer(this, 0, 0);
      fern1.addBox(-8F, -16F, 0F, 16, 16, 0);
      fern1.setRotationPoint(0F, 24F, 4F);
      fern1.setTextureSize(128, 64);
      fern1.mirror = true;
      setRotation(fern1, -0.3346075F, 0.2974289F, 0F);
      fern2 = new ModelRenderer(this, 0, 0);
      fern2.addBox(0F, -16F, -8F, 0, 16, 16);
      fern2.setRotationPoint(4F, 24F, 2F);
      fern2.setTextureSize(128, 64);
      fern2.mirror = true;
      setRotation(fern2, 0F, -0.2974289F, 0.2602503F);
      fern3 = new ModelRenderer(this, 33, 0);
      fern3.addBox(0F, -16F, -8F, 0, 16, 16);
      fern3.setRotationPoint(2F, 24F, -2F);
      fern3.setTextureSize(128, 64);
      fern3.mirror = true;
      setRotation(fern3, 0F, 0.5948578F, 0.4461433F);
      fern4 = new ModelRenderer(this, 33, 0);
      fern4.addBox(-8F, -16F, 0F, 16, 16, 0);
      fern4.setRotationPoint(0F, 24F, -4F);
      fern4.setTextureSize(128, 64);
      fern4.mirror = true;
      setRotation(fern4, 0.7063936F, 0.4461433F, 0F);
      fern5 = new ModelRenderer(this, 66, 0);
      fern5.addBox(0F, -16F, -8F, 0, 16, 16);
      fern5.setRotationPoint(-4F, 24F, 0F);
      fern5.setTextureSize(128, 64);
      fern5.mirror = true;
      setRotation(fern5, 0F, 0.1115358F, -0.5576792F);
      fern6 = new ModelRenderer(this, 66, -16);
      fern6.addBox(0F, -16F, -8F, 0, 16, 16);
      fern6.setRotationPoint(-3F, 24F, 0F);
      fern6.setTextureSize(128, 64);
      fern6.mirror = true;
      setRotation(fern6, 0F, -0.3346075F, -0.2974289F);
      fern7 = new ModelRenderer(this, 33, 32);
      fern7.addBox(-8F, -16F, 0F, 16, 16, 0);
      fern7.setRotationPoint(0F, 24F, 2F);
      fern7.setTextureSize(128, 64);
      fern7.mirror = true;
      setRotation(fern7, -0.2974289F, -0.2974289F, 0F);
      fern8 = new ModelRenderer(this, 33, 32);
      fern8.addBox(0F, -16F, -8F, 0, 16, 16);
      fern8.setRotationPoint(2F, 24F, 0F);
      fern8.setTextureSize(128, 64);
      fern8.mirror = true;
      setRotation(fern8, 0F, 0.3717861F, 0.2974289F);
      middle1 = new ModelRenderer(this, 0, 40);
      middle1.addBox(-8F, -20F, 0F, 16, 20, 0);
      middle1.setRotationPoint(0F, 24F, 0F);
      middle1.setTextureSize(128, 64);
      middle1.mirror = true;
      setRotation(middle1, 0F, 0F, 0F);
      middle2 = new ModelRenderer(this, 66, 21);
      middle2.addBox(0F, -20F, -8F, 0, 20, 16);
      middle2.setRotationPoint(0F, 24F, 0F);
      middle2.setTextureSize(128, 64);
      middle2.mirror = true;
      setRotation(middle2, 0F, 0F, 0F);
  }
  
  /*public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5);
    fern1.render(f5);
    fern2.render(f5);
    fern3.render(f5);
    fern4.render(f5);
    fern5.render(f5);
    fern6.render(f5);
    fern7.render(f5);
    fern8.render(f5);
    middle1.render(f5);
    middle2.render(f5);
  }*/
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
 /* public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5);
  }*/

}
