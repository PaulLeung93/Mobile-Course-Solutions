"""
Generates procedural sprite sheets for Rogue weapons:
  - shuriken_sheet.png       (standard LPC 13x21 walk/cast sheet, 64px cells)
  - shuriken_attack_sheet.png(192px-cell throw projectile, 7 frames on row 2)
  - sickle_sheet.png         (standard LPC 13x21 walk/slash sheet, 64px cells)
"""
from PIL import Image, ImageDraw, ImageFilter
import math
import os

SCRIPT_DIR   = os.path.dirname(os.path.abspath(__file__))
WEAPONS_DIR  = os.path.join(SCRIPT_DIR, "../app/src/main/assets/sprites/weapons")

# LPC layout constants
CELL          = 64
LPC_COLS      = 13
LPC_ROWS      = 21
SHEET_W       = LPC_COLS * CELL   # 832
SHEET_H       = LPC_ROWS * CELL   # 1344

WALK_ROW      = 10
WALK_FRAMES   = 9
SLASH_ROW     = 14
SLASH_FRAMES  = 6
SPELLCAST_ROW = 2
SPELLCAST_FRAMES = 7


# ── Shuriken helpers ──────────────────────────────────────────────────────────

def draw_shuriken(draw, cx, cy, size=8, angle_deg=0, alpha=255):
    """4-pointed throwing star with a metallic silver look."""
    outer_color = (80, 80, 100, alpha)
    inner_color = (210, 210, 225, alpha)
    outer_pts, inner_pts = [], []
    for i in range(8):
        r_outer = size if i % 2 == 0 else max(2, size // 3)
        r_inner = max(1, size - 1) if i % 2 == 0 else max(1, size // 4)
        a = math.radians(angle_deg + i * 45)
        outer_pts.append((cx + r_outer * math.cos(a), cy + r_outer * math.sin(a)))
        inner_pts.append((cx + r_inner * math.cos(a), cy + r_inner * math.sin(a)))
    draw.polygon(outer_pts, fill=outer_color)
    draw.polygon(inner_pts, fill=inner_color)
    # Center dot
    draw.ellipse([cx - 2, cy - 2, cx + 2, cy + 2], fill=(150, 150, 160, alpha))


def make_shuriken_walk_sheet():
    sheet = Image.new('RGBA', (SHEET_W, SHEET_H), (0, 0, 0, 0))
    draw  = ImageDraw.Draw(sheet)

    # Walk south (row 10) — shuriken held at hip, subtle bob each frame
    bob = [0, -1, -2, -1, 0, 1, 2, 1, 0]
    for f in range(WALK_FRAMES):
        cx = f * CELL + 36
        cy = WALK_ROW * CELL + 46 + bob[f]
        draw_shuriken(draw, cx, cy, size=7, angle_deg=f * 5)

    # Spellcast south (row 2) — arm raised, winding up the throw
    for f in range(SPELLCAST_FRAMES):
        cx = f * CELL + 36 - f
        cy = SPELLCAST_ROW * CELL + 42 - f * 2
        draw_shuriken(draw, cx, cy, size=8, angle_deg=f * 15)

    sheet.save(os.path.join(WEAPONS_DIR, "shuriken_sheet.png"))
    print("  shuriken_sheet.png")


def make_shuriken_attack_sheet():
    """1344×768 sheet — spinning shuriken projectile on row 2, 7 frames."""
    ACELL = 192
    W, H  = SPELLCAST_FRAMES * ACELL, 4 * ACELL   # 1344 × 768
    sheet = Image.new('RGBA', (W, H), (0, 0, 0, 0))
    draw  = ImageDraw.Draw(sheet)
    y_off = 2 * ACELL   # row 2

    for frame in range(SPELLCAST_FRAMES):
        # Projectile travels right and slightly up; spins ~51° per frame
        cx = frame * ACELL + 78 + frame * 16
        cy = y_off + 138 - frame * 2
        rot  = frame * 51
        size = max(6, 14 - frame)
        alpha = max(120, 255 - frame * 18)

        draw_shuriken(draw, cx, cy, size=size, angle_deg=rot, alpha=alpha)

        # Motion trail from previous frame position
        if frame > 0:
            draw_shuriken(draw, cx - 12, cy + 2,
                          size=max(3, size - 3), angle_deg=rot - 35, alpha=55)

    sheet.save(os.path.join(WEAPONS_DIR, "shuriken_attack_sheet.png"))
    print("  shuriken_attack_sheet.png")


# ── Sickle helpers ────────────────────────────────────────────────────────────

def draw_sickle(draw, cx, cy, angle_deg=0, alpha=255):
    """Curved sickle blade + short wooden handle."""
    blade_fill    = (175, 165, 70, alpha)
    blade_outline = (100, 90, 30, alpha)
    handle_color  = (90,  60, 30, alpha)

    # Handle — a short diagonal line below the blade pivot
    ha = math.radians(angle_deg - 50)
    draw.line(
        [(cx, cy), (cx + 9 * math.cos(ha), cy + 9 * math.sin(ha))],
        fill=handle_color, width=2
    )

    # Blade — a 200-degree arc polygon (outer edge r≈10, inner edge r≈7)
    outer, inner = [], []
    for i in range(11):
        t = i / 10
        a = math.radians(angle_deg + 85 + t * 200)
        outer.append((cx + (10 + t * 2) * math.cos(a), cy + (10 + t * 2) * math.sin(a)))
    for i in range(10, -1, -1):
        t = i / 10
        a = math.radians(angle_deg + 85 + t * 200)
        inner.append((cx + 6.5 * math.cos(a), cy + 6.5 * math.sin(a)))
    pts = outer + inner
    if len(pts) >= 3:
        draw.polygon(pts, fill=blade_fill, outline=blade_outline)


def make_sickle_walk_sheet():
    sheet = Image.new('RGBA', (SHEET_W, SHEET_H), (0, 0, 0, 0))
    draw  = ImageDraw.Draw(sheet)

    # Walk south (row 10) — sickle at hip with subtle bob
    bob = [0, -1, -2, -1, 0, 1, 2, 1, 0]
    for f in range(WALK_FRAMES):
        cx = f * CELL + 37
        cy = WALK_ROW * CELL + 47 + bob[f]
        draw_sickle(draw, cx, cy, angle_deg=f * 3)

    # Slash south (row 14) — sickle sweeps forward in an arc
    for f in range(SLASH_FRAMES):
        cx = f * CELL + 38
        cy = SLASH_ROW * CELL + 46 - f * 2
        draw_sickle(draw, cx, cy, angle_deg=-25 + f * 30)

    # Spellcast south (row 2) — sickle raised (ability cast stance)
    for f in range(SPELLCAST_FRAMES):
        cx = f * CELL + 36
        cy = SPELLCAST_ROW * CELL + 43 - f * 2
        draw_sickle(draw, cx, cy, angle_deg=-40 + f * 10)

    sheet.save(os.path.join(WEAPONS_DIR, "sickle_sheet.png"))
    print("  sickle_sheet.png")


# ── Entry point ───────────────────────────────────────────────────────────────

if __name__ == "__main__":
    os.makedirs(WEAPONS_DIR, exist_ok=True)
    print("Generating Rogue weapon sheets …")
    make_shuriken_walk_sheet()
    make_shuriken_attack_sheet()
    make_sickle_walk_sheet()
    print("Done.")
