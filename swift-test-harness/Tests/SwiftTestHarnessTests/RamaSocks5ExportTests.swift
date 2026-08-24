import Testing
import RamaSocks5

@Suite struct RamaSocks5ExportTests {
    @Test func testSwiftModuleLoads() {
        #expect(Bool(true), "RamaSocks5 swift module imported cleanly")
    }
}

