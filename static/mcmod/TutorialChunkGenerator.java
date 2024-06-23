package com.mcjty.mytutorial.dimension;

import com.mcjty.mytutorial.MyTutorial;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.Blockreader;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.WorldGenRegion;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TutorialChunkGenerator extends ChunkGenerator {

    private static final CodecSettings SETTINGS_CODEC = RecordCodecBuilder.create(instance -
            instance.group(
                    Codec.INT.fieldOf(base).forGetter(SettingsgetBaseHeight),
                    Codec.FLOAT.fieldOf(verticalvariance).forGetter(SettingsgetVerticalVariance),
                    Codec.FLOAT.fieldOf(horizontalvariance).forGetter(SettingsgetHorizontalVariance)
            ).apply(instance, Settingsnew));

    public static final CodecTutorialChunkGenerator CODEC = RecordCodecBuilder.create(instance -
            instance.group(
                    RegistryLookupCodec.create(Registry.BIOME_REGISTRY).forGetter(TutorialChunkGeneratorgetBiomeRegistry),
                    SETTINGS_CODEC.fieldOf(settings).forGetter(TutorialChunkGeneratorgetTutorialSettings)
            ).apply(instance, TutorialChunkGeneratornew));

    private final Settings settings;

    public TutorialChunkGenerator(RegistryBiome registry, Settings settings) {
        super(new TutorialBiomeProvider(registry), new DimensionStructuresSettings(false));
        this.settings = settings;
        MyTutorial.LOGGER.info(Chunk generator settings  + settings.getBaseHeight() + ,  + settings.getHorizontalVariance() + ,  + settings.getVerticalVariance());
    }

    public Settings getTutorialSettings() {
        return settings;
    }

    public RegistryBiome getBiomeRegistry() {
        return ((TutorialBiomeProvider)biomeSource).getBiomeRegistry();
    }

    public static ListListInteger blockPoses = new ArrayList();

    static {
        for(int i=0;i10000;i++) {
            blockPoses.add(Arrays.asList((int)(50Math.cos(i10000d4Math.PI)), (int)(i100d), (int)(50Math.sin(i10000d4Math.PI))));
        }
        double size = 100;
        double baseZ = 50;
        double a = 20;
        double b = 10;
        double csqr = 9;
        for(int i=0;isize2;i++) {
            for(int j=0;jsize;j++) {
                blockPoses.add(Arrays.asList((int)(aMath.sin(isizeMath.PI)Math.cos(jsize2Math.PI)),
                        (int)(csqrMath.pow(Math.cos(isizeMath.PI), 2)+baseZ),
                        (int)(bMath.sin(isizeMath.PI)Math.sin(jsize2Math.PI)),
                        0));
            }
        }
        size = 1000;
        for(int i=0;isize;i++) {
            blockPoses.add(Arrays.asList((int)(aMath.cos(isize2Math.PI)),
                    (int)(abcsqrMath.pow(Math.cos(isizeMath.PI2), 2)Math.cos(isize2Math.PI2)
                    Math.sqrt(Math.pow(aMath.sin(isizeMath.PI2), 2)+Math.pow(bMath.cos(isizeMath.PI2), 2))+baseZ),
                    (int)(bMath.sin(isize2Math.PI)),0
                    1));
        }
        double xoffset=20;
        double yoffset=10;
        double size = 1000;
        double baseZ = 55;
        double c = 50;
        double r = 25;
        double offset = 10;
        double csq = cc;
        double scale = 400;
        for(int i=0;isize2;i++) {
            for(int j=0;jsize;j++) {
                double x = (cMath.sin(isizeMath.PI)Math.cos(jsize2Math.PI)+offset);
                double y = (cMath.cos(isizeMath.PI)+baseZ);
                double z = (cMath.sin(isizeMath.PI)Math.sin(jsize2Math.PI));
                if(Math.pow(x-xoffset, 2)+Math.pow(z-yoffset, 2)=rr)
                    blockPoses.add(Arrays.asList((int)x,
                            (int)y,
                            (int)z,
                            0));
            }
        }
        double sinI;
        double cosI;
        for(int i=0;isize;i++) {
            sinI = Math.sin(isize2Math.PI);
            cosI = Math.cos(isize2Math.PI);
            System.out.println((int)((((csq-Math.pow(rsinI+yoffset, 2))(rcosI+xoffset)cosI-Math.pow((rcosI+xoffset-offset), 2)(rsinI+yoffset)sinI))scale+baseZ));
            blockPoses.add(Arrays.asList((int)(rcosI+xoffset),
                    (int)((((csq-Math.pow(rsinI+yoffset, 2))(rcosI+xoffset)cosI-Math.pow((rcosI+xoffset-offset), 2)(rsinI+yoffset)sinI))scale+baseZ),
                    (int)(rsinI+yoffset),
                    1));
        }
    }

    @Override
    public void buildSurfaceAndBedrock(WorldGenRegion region, IChunk chunk) {
        BlockState bedrock = Blocks.BEDROCK.defaultBlockState();
        BlockState stone = Blocks.LAPIS_BLOCK.defaultBlockState();
        BlockState redStoneBlock = Blocks.REDSTONE_BLOCK.defaultBlockState();
        ChunkPos chunkpos = chunk.getPos();

        BlockPos.Mutable pos = new BlockPos.Mutable();

        for (ListInteger positions  blockPoses) {
            if(positions.get(0)=chunkpos.x  16 && positions.get(0)chunkpos.x  16+16 &&
                    positions.get(2)=chunkpos.z  16 && positions.get(2)chunkpos.z  16+16) {
                System.out.println(positions);
                chunk.setBlockState(pos.set(positions.get(0)-chunkpos.x  16, positions.get(1), positions.get(2)-chunkpos.z  16), positions.get(3)==1redStoneBlockstone, false);
            }
        }

        int x;
        int z;

        for (x = 0; x  16; x++) {
            for (z = 0; z  16; z++) {
                chunk.setBlockState(pos.set(x, 0, z), bedrock, false);
            }
        }

        int baseHeight = settings.getBaseHeight();
        float verticalVariance = settings.getVerticalVariance();
        float horizontalVariance = settings.getHorizontalVariance();
        System.out.println(verticalVariance+ +horizontalVariance);
        for (x = 0; x  16; x++) {
            for (z = 0; z  16; z++) {
                int realx = chunkpos.x  16 + x;
                int realz = chunkpos.z  16 + z;
                int height = (int) (baseHeight + Math.sin(realx  horizontalVariance)verticalVariance + Math.cos(realz  horizontalVariance)verticalVariance);
                for (int y = 1 ; y  height ; y++) {
                    chunk.setBlockState(pos.set(x, y, z), stone, false);
                }
            }
        }

    }

    @Override
    protected Codec extends ChunkGenerator codec() {
        return CODEC;
    }

    @Override
    public ChunkGenerator withSeed(long seed) {
        return new TutorialChunkGenerator(getBiomeRegistry(), settings);
    }

    @Override
    public void fillFromNoise(IWorld world, StructureManager structureManager, IChunk chunk) {

    }

    @Override
    public int getBaseHeight(int x, int z, Heightmap.Type heightmapType) {
        return 0;
    }

    @Override
    public IBlockReader getBaseColumn(int p_230348_1_, int p_230348_2_) {
        return new Blockreader(new BlockState[0]);
    }

    private static class Settings {
        private final int baseHeight;
        private final float verticalVariance;
        private final float horizontalVariance;

        public Settings(int baseHeight, float verticalVariance, float horizontalVariance) {
            this.baseHeight = baseHeight;
            this.verticalVariance = verticalVariance;
            this.horizontalVariance = horizontalVariance;
        }

        public float getVerticalVariance() {
            return verticalVariance;
        }

        public int getBaseHeight() {
            return baseHeight;
        }

        public float getHorizontalVariance() {
            return horizontalVariance;
        }
    }
}
