import { Container } from "react-bootstrap";
import { Route, Routes } from "react-router-dom";
import { ToastContainer } from "react-toastify";
import Home from "./components/home";
import Forum from "./components/forum";

function App() {
  return (
    <div className="bodyApp">
      <Container className="mt-4">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/forum" element={<Forum />} />
        </Routes>
        <ToastContainer />
      </Container>
    </div>
  );
}

export default App;
