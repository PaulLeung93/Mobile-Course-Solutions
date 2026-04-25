"""
Generates procedural ability overlay sheets for the Rogue class.
All sheets: 1344×768 (7 frames × 192px cells), animation on row 2 only.

  vanish_sheet.png        — copy of smoke_bomb_attack_sheet.png
  poison_strike_sheet.png — copy of poison_vial_attack_sheet.png
  shadowstep_sheet.png    — dark purple shadowy portal
  backstab_sheet.png      — red/gold critical-hit burst
"""
from PIL import Image, ImageDraw, ImageFilter
import math
import os
import shutil
import random

SCRIPT_DIR   = os.path.dirname(os.path.abspath(__file__))
ABILITIES_DIR = os.path.join(SCRIPT_DIR, "../app/src/main/assets/sprites/abilities")
WEAPONS_DIR   = os.path.join(SCRIPT_DIR, "../app/src/main/assets/sprites/weapons")

CELL   = 192
FRAMES = 7
ROWS   = 4
W      = FRAMES * CELL   # 1344
H      = ROWS   * CELL   # 768
ROW2_Y = 2 * CELL        # 384  (south-facing row)


# ── Vanish / Poison Strike ────────────────────────────────────────────────────

def make_vanish_sheet():
    src = os.path.join(WEAPONS_DIR, "smoke_bomb_attack_sheet.png")
    dst = os.path.join(ABILITIES_DIR, "vanish_sheet.png")
    shutil.copy2(src, dst)
    print("  vanish_sheet.png  (copied from smoke_bomb_attack_sheet)")


def make_poison_strike_sheet():
    src = os.path.join(WEAPONS_DIR, "poison_vial_attack_sheet.png")
    dst = os.path.join(ABILITIES_DIR, "poison_strike_sheet.png")
    shutil.copy2(src, dst)
    print("  poison_strike_sheet.png  (copied from poison_vial_attack_sheet)")


# ── Shadowstep ────────────────────────────────────────────────────────────────

def make_shadowstep_sheet():
    """
    Dark purple shadow portal:
      frames 0-3 → portal expands with tendrils
      frames 4-6 → portal collapses / fades
    """
    sheet = Image.new('RGBA', (W, H), (0, 0, 0, 0))

    for frame in range(FRAMES):
        layer = Image.new('RGBA', (CELL, CELL), (0, 0, 0, 0))
        draw  = ImageDraw.Draw(layer)
        cx, cy = CELL // 2, CELL // 2   # 96, 96

        # Portal radius & opacity curves
        if frame <= 3:
            radius    = 12 + frame * 21
            intensity = 160 + frame * 18
        else:
            radius    = max(8, 75 - (frame - 3) * 22)
            intensity = max(60, 214 - (frame - 3) * 65)

        # Outer dark glow halo
        for r in range(radius + 22, radius, -3):
            a = max(0, int(intensity * 0.45 * (1 - (r - radius) / 22)))
            draw.ellipse([cx - r, cy - r, cx + r, cy + r], fill=(20, 0, 45, a))

        # Core portal disc
        draw.ellipse([cx - radius, cy - radius, cx + radius, cy + radius],
                     fill=(12, 0, 35, intensity))

        # Bright purple rim
        rim = 4
        draw.ellipse([cx - radius - rim, cy - radius - rim,
                      cx + radius + rim, cy + radius + rim],
                     outline=(110, 0, 180, intensity), width=rim)

        # Shadow tendrils
        for t in range(7):
            angle  = math.radians(frame * 22 + t * (360 / 7))
            length = radius + 10 + frame * 7
            ex     = cx + int(length * math.cos(angle))
            ey     = cy + int(length * math.sin(angle))
            a      = max(0, intensity - 50)
            draw.line([(cx, cy), (ex, ey)], fill=(55, 0, 90, a), width=2)

        # Floating dark particles (seeded for consistency)
        rng = random.Random(frame * 7 + 13)
        for _ in range(6):
            dist = rng.randint(radius // 2, min(88, radius + 18))
            ang  = rng.uniform(0, math.tau)
            px   = cx + int(dist * math.cos(ang))
            py   = cy + int(dist * math.sin(ang))
            pa   = rng.randint(max(0, intensity - 70), intensity)
            draw.ellipse([px - 2, py - 2, px + 2, py + 2], fill=(70, 0, 120, pa))

        layer = layer.filter(ImageFilter.GaussianBlur(radius=2))
        sheet.paste(layer, (frame * CELL, ROW2_Y), layer)

    sheet.save(os.path.join(ABILITIES_DIR, "shadowstep_sheet.png"))
    print("  shadowstep_sheet.png")


# ── Backstab ──────────────────────────────────────────────────────────────────

def make_backstab_sheet():
    """
    Critical-hit burst:
      frames 0-2 → burst builds up (red/orange core expands)
      frames 3-4 → peak — full starburst with gold rays
      frames 5-6 → burst fades, sparks fly outward
    """
    sheet = Image.new('RGBA', (W, H), (0, 0, 0, 0))

    for frame in range(FRAMES):
        layer = Image.new('RGBA', (CELL, CELL), (0, 0, 0, 0))
        draw  = ImageDraw.Draw(layer)
        cx, cy = CELL // 2, CELL // 2

        # Burst radius & intensity curves
        if frame <= 2:
            burst_r   = 18 + frame * 24
            intensity = 140 + frame * 35
        elif frame <= 4:
            burst_r   = 68 + (frame - 2) * 6
            intensity = 215
        else:
            burst_r   = max(0, 80 - (frame - 4) * 28)
            intensity = max(50, 215 - (frame - 4) * 82)

        if burst_r > 0:
            # Core radial gradient: hot white centre → red → orange
            for r in range(burst_r, 0, -3):
                frac  = r / burst_r
                alpha = int(intensity * (1 - frac * 0.55))
                red   = min(255, 255)
                green = min(255, int(200 * (1 - frac * 0.7)))
                draw.ellipse([cx - r, cy - r, cx + r, cy + r],
                             fill=(red, green, 0, alpha))

            # Gold starburst rays (8 rays, rotate slightly per frame)
            for s in range(8):
                angle  = math.radians(frame * 12 + s * 45)
                inner  = burst_r // 2
                outer  = burst_r + 18
                sx     = cx + int(inner * math.cos(angle))
                sy     = cy + int(inner * math.sin(angle))
                ex     = cx + int(outer * math.cos(angle))
                ey     = cy + int(outer * math.sin(angle))
                a      = max(0, intensity - 25)
                draw.line([(sx, sy), (ex, ey)], fill=(255, 210, 0, a), width=2)

        # Scattered spark dots (randomised but seeded)
        rng = random.Random(frame * 19 + 5)
        num_sparks = 8 + frame * 2
        for _ in range(num_sparks):
            dist = rng.randint(max(0, burst_r // 2), min(90, burst_r + 32))
            ang  = rng.uniform(0, math.tau)
            px   = cx + int(dist * math.cos(ang))
            py   = cy + int(dist * math.sin(ang))
            pa   = rng.randint(max(0, intensity - 55), intensity)
            draw.ellipse([px - 2, py - 2, px + 2, py + 2], fill=(255, 230, 60, pa))

        layer = layer.filter(ImageFilter.GaussianBlur(radius=1))
        sheet.paste(layer, (frame * CELL, ROW2_Y), layer)

    sheet.save(os.path.join(ABILITIES_DIR, "backstab_sheet.png"))
    print("  backstab_sheet.png")


# ── Entry point ───────────────────────────────────────────────────────────────

if __name__ == "__main__":
    os.makedirs(ABILITIES_DIR, exist_ok=True)
    print("Generating Rogue ability sheets …")
    make_vanish_sheet()
    make_poison_strike_sheet()
    make_shadowstep_sheet()
    make_backstab_sheet()
    print("Done.")
