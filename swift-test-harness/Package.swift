// swift-tools-version: 6.0
import PackageDescription

let package = Package(
    name: "SwiftTestHarness",
    platforms: [.macOS("15.0")],
    dependencies: [
        .package(name: "RamaSocks5", path: "../build/SPMPackage/macosArm64/Debug")
    ],
    targets: [
        .testTarget(
            name: "SwiftTestHarnessTests",
            dependencies: [
                .product(name: "RamaSocks5Library", package: "RamaSocks5")
            ],
            linkerSettings: [
                .unsafeFlags([
                    "-L", "../build/swift-test",
                    "-L", "../build/MergedLibraries/macos/Debug",
                    "-lRamaSocks5",
                ]),
            ]
        ),
    ]
)