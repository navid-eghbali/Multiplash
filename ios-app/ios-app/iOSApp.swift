import SwiftUI
import SharedKit

class AppDelegate: UIResponder, UIApplicationDelegate {
    
    lazy var applicationComponent: DarwinApplicationComponent = createApplicationComponent(appDelegate: self)
    
    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions _: [UIApplication.LaunchOptionsKey : Any]? = nil
    ) -> Bool {
        return true
    }
}

@main
struct iOSApp: App {
    
    @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate
    
	var body: some Scene {
		WindowGroup {
            let dispatchers = delegate.applicationComponent.dispatchers
			ContentView()
		}
	}
}

private func createApplicationComponent(appDelegate: AppDelegate) -> DarwinApplicationComponent {
    return DarwinApplicationComponent.companion.create()
}
