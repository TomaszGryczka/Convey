import { Navigate, Outlet } from "react-router-dom"
import { isJWTValidated } from "../Util/Util";

const ProtectedRoute = () => {
    return isJWTValidated() ? <Navigate to="/login" /> : <Outlet />;
}

export default ProtectedRoute;