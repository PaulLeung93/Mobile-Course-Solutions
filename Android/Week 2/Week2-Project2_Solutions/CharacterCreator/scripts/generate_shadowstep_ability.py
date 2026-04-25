"""
Generates shadowstep_sheet.png — a purple/black recolor of the Nature's Call
animation style: scattered shadow particles + a dark glowing dome.

Sheet layout: 1344×768, animation on row 2 (y_offset=384), 7 frames × 192px cells.
"""
from PIL import Image, ImageDraw, ImageFilter
import math, random, os

SCRIPT_DIR   = os.path.dirname(os.path.abspath(__file__))
ABILITIES_DIR = os.path.join(SCRIPT_DIR, "../app/src/main/assets/sprites/abilities")

CELL   = 192
FRAMES = 7
W      = FRAMES * CELL   # 1344
H      = 4 * CELL        # 768
ROW2_Y = 2 * CELL        # 384

# Character anchor within the 192×192 cell (torso/hip area, matching Nature's Call)
CHAR_CX = CELL // 2        # 96
CHAR_CY = CELL - 52        # 140


def draw_particles(draw, frame, rng, alpha_scale):
    """Scatter shadow dots around the character in an oval cloud."""
    # Particle count: dense in early frames, thinning out later
    counts = [32, 28, 24, 20, 16, 11, 7]
    n = counts[frame]

    for _ in range(n):
        # Oval spread: wider horizontally, tighter vertically
        spread_x = 30 + frame * 8
        spread_y = 15 + frame * 4
        px = CHAR_CX + int(rng.gauss(0, spread_x))
        py = CHAR_CY + int(rng.gauss(0, spread_y))

        # Vary size: larger dots close to center, smaller far out
        dist = math.hypot(px - CHAR_CX, py - CHAR_CY)
        size = max(2, int(rng.uniform(3, 11) * max(0.4, 1 - dist / 90)))

        # Alternate between vivid purple and deep shadow-purple
        if rng.random() < 0.55:
            r, g, b = 140, 0, 180   # vivid purple
        else:
            r, g, b = 55, 0, 80     # deep shadow purple

        a = int(rng.uniform(0.55, 1.0) * alpha_scale * 255)
        draw.ellipse([px - size, py - size, px + size, py + size], fill=(r, g, b, a))


def draw_glow_dome(layer, frame, alpha_scale):
    """Soft glowing oval that builds in the middle frames and fades out."""
    # Glow envelope: 0→0, 1→0.4, 2→0.85, 3→1.0, 4→0.75, 5→0.45, 6→0.15
    envelopes = [0.0, 0.40, 0.85, 1.0, 0.75, 0.45, 0.15]
    strength  = envelopes[frame] * alpha_scale

    if strength <= 0:
        return

    glow = Image.new('RGBA', (CELL, CELL), (0, 0, 0, 0))
    gdraw = ImageDraw.Draw(glow)

    # Outer soft halo (wide oval)
    ow, oh = 72, 36
    ox, oy = CHAR_CX, CHAR_CY - 12
    for r in range(int(oh), 0, -3):
        frac  = r / oh
        alpha = int(strength * 100 * (1 - frac))
        gdraw.ellipse(
            [ox - int(ow * frac), oy - r, ox + int(ow * frac), oy + r],
            fill=(60, 0, 100, alpha)
        )

    # Inner bright core (smaller, more vivid)
    iw, ih = 38, 18
    for r in range(ih, 0, -2):
        frac  = r / ih
        alpha = int(strength * 180 * (1 - frac * 0.5))
        gdraw.ellipse(
            [ox - int(iw * frac), oy - r, ox + int(iw * frac), oy + r],
            fill=(160, 40, 220, alpha)
        )

    # Bright central hotspot
    cs = int(strength * 8)
    if cs > 0:
        gdraw.ellipse(
            [ox - cs, oy - cs // 2, ox + cs, oy + cs // 2],
            fill=(230, 120, 255, int(strength * 220))
        )

    glow = glow.filter(ImageFilter.GaussianBlur(radius=5))
    layer.alpha_composite(glow)


def make_shadowstep_sheet():
    sheet = Image.new('RGBA', (W, H), (0, 0, 0, 0))

    # Alpha scale: full during mid-frames, fading at ends
    alpha_scales = [0.90, 1.0, 1.0, 1.0, 0.80, 0.55, 0.30]

    for frame in range(FRAMES):
        layer = Image.new('RGBA', (CELL, CELL), (0, 0, 0, 0))
        draw  = ImageDraw.Draw(layer)
        rng   = random.Random(frame * 31 + 7)   # seeded for deterministic output

        scale = alpha_scales[frame]
        draw_particles(draw, frame, rng, scale)
        draw_glow_dome(layer, frame, scale)

        sheet.paste(layer, (frame * CELL, ROW2_Y), layer)

    out = os.path.join(ABILITIES_DIR, "shadowstep_sheet.png")
    sheet.save(out)
    print(f"  shadowstep_sheet.png  ->  {out}")


if __name__ == "__main__":
    os.makedirs(ABILITIES_DIR, exist_ok=True)
    print("Generating Shadowstep ability sheet …")
    make_shadowstep_sheet()
    print("Done.")
