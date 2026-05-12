import SwiftUI

// MARK: - Design Tokens

/// Centralized design system for SwiftPrimerApp.
/// Uses Swift Orange as the primary accent to distinguish the iOS track
/// from the Android (Kotlin Purple) version.
enum Theme {

    // ──────────────────────────────────────────────
    //  Accent Colors
    // ──────────────────────────────────────────────
    static let swiftOrange  = Color(hex: "F05138")
    static let accentBlue   = Color(hex: "3B82F6")
    static let successGreen = Color(hex: "3ECF8E")
    static let warningAmber = Color(hex: "F59E0B")

    // ──────────────────────────────────────────────
    //  Background & Surface
    // ──────────────────────────────────────────────
    static let backgroundPrimary   = Color(hex: "0F1119")
    static let backgroundSecondary = Color(hex: "161822")
    static let backgroundTertiary  = Color(hex: "1C1F2E")
    static let cardBackground      = Color(hex: "1C1F2E")

    // ──────────────────────────────────────────────
    //  Text
    // ──────────────────────────────────────────────
    static let textPrimary   = Color(hex: "E2E8F0")
    static let textSecondary = Color(hex: "94A3B8")
    static let textMuted     = Color(hex: "64748B")

    // ──────────────────────────────────────────────
    //  Borders
    // ──────────────────────────────────────────────
    static let border        = Color(hex: "2E3245")
    static let borderSubtle  = Color(hex: "232636")

    // ──────────────────────────────────────────────
    //  Code Block
    // ──────────────────────────────────────────────
    static let codeBg     = Color(hex: "1A1D27")
    static let codeHeader = Color(hex: "252836")
    static let codeText   = Color(hex: "CDD6F4")

    // ──────────────────────────────────────────────
    //  Corner Radii
    // ──────────────────────────────────────────────
    static let radiusSmall:  CGFloat = 8
    static let radiusMedium: CGFloat = 12
    static let radiusLarge:  CGFloat = 16

    // ──────────────────────────────────────────────
    //  Spacing
    // ──────────────────────────────────────────────
    static let spacingXS:  CGFloat = 4
    static let spacingSM:  CGFloat = 8
    static let spacingMD:  CGFloat = 16
    static let spacingLG:  CGFloat = 24
    static let spacingXL:  CGFloat = 32

    // ──────────────────────────────────────────────
    //  Tab Bar Icons (SF Symbols)
    // ──────────────────────────────────────────────
    static let tabHome      = "house.fill"
    static let tabLearn     = "book.fill"
    static let tabReference = "doc.text.fill"
    static let tabCode      = "chevron.left.forwardslash.chevron.right"
}

// MARK: - Color Extension

extension Color {
    /// Create a Color from a hex string (e.g. "F05138").
    init(hex: String) {
        let hex = hex.trimmingCharacters(in: CharacterSet.alphanumerics.inverted)
        var int: UInt64 = 0
        Scanner(string: hex).scanHexInt64(&int)
        let r, g, b, a: UInt64
        switch hex.count {
        case 6:
            (r, g, b, a) = (int >> 16, int >> 8 & 0xFF, int & 0xFF, 255)
        case 8:
            (r, g, b, a) = (int >> 24, int >> 16 & 0xFF, int >> 8 & 0xFF, int & 0xFF)
        default:
            (r, g, b, a) = (0, 0, 0, 255)
        }
        self.init(
            .sRGB,
            red:     Double(r) / 255,
            green:   Double(g) / 255,
            blue:    Double(b) / 255,
            opacity: Double(a) / 255
        )
    }
}

// MARK: - View Modifiers

/// A card-style background modifier used throughout the app.
struct CardModifier: ViewModifier {
    var padding: CGFloat = Theme.spacingMD
    func body(content: Content) -> some View {
        content
            .padding(padding)
            .background(Theme.cardBackground)
            .clipShape(RoundedRectangle(cornerRadius: Theme.radiusMedium))
            .overlay(
                RoundedRectangle(cornerRadius: Theme.radiusMedium)
                    .stroke(Theme.border, lineWidth: 1)
            )
    }
}

extension View {
    func cardStyle(padding: CGFloat = Theme.spacingMD) -> some View {
        modifier(CardModifier(padding: padding))
    }
}
