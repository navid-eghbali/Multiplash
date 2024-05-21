import SwiftUI
import SharedKit

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> some UIViewController {
        MainViewControllerKt.MainViewController()
    }
    
    func updateUIViewController(_ uiViewController: UIViewControllerType, context: Context) {}
}

struct ContentView: View {
	var body: some View {
        ComposeView().ignoresSafeArea(.keyboard)
	}
}
