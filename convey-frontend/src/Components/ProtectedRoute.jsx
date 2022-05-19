import { Navigate, Outlet } from "react-router-dom"

const ProtectedRoute = () => {

    return localStorage.getItem("accessToken") === null ? <Navigate to="/login" /> : <Outlet/>;
}

export default ProtectedRoute;