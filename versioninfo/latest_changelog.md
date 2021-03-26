## 2.2.0 - Chipped Blackstone

**For Minecraft 1.16.5**

Introducing blackstone rocks and flat rocks in the Nether, along with several major bug fixes and performance improvements!

#### Changelog

- Updated Minecraft to 1.16.5.
- Updated Minecraft Forge to 36.1.0.
- Forgo using JS ASM entirely in favor of using mixins.
- Removed an unnecessary LivingEntity check for the leek trigger (#74).
- Gave a name to the village houses loot pool (#75).
- Removed VerificationUtil since it is unnecessary for this mod.
- Added leek to crop tag (#78).
- Better organized the mixins to prevent some dev-only mixins from activating in a normal environment.
- Added Blackstone Rock.
- Added Flat Blackstone Rock.
- Changed most Supplies to Lazies, should help with world loading.
- Blackstone rocks and flat rocks generate in small batches in the Nether.
- Improved the hitboxes for the decorative blocks.
- Fixed stick blocks all facing a single direction.
- Fixed decorative blocks being able to be pushed by pistons.
- Rocks and sticks now use a custom block placer to avoid random incorrect placements.
- Fixed the paving stones Patchouli book entry not being able to be completed.
