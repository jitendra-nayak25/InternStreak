import { useState } from "react";
import { loginUser } from "./services/api";

function Login() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [message, setMessage] = useState("");

    const handleLogin = async (e) => {
        e.preventDefault();

        try {
            const data = await loginUser(username, password);

            localStorage.setItem("token", data.token);

            setMessage("Login successful!");
            console.log("JWT Token:", data.token);

        } catch (error) {
            setMessage(error.message);
        }
    };

    return (
        <div>
            <h2>Course Management Login</h2>

            <form onSubmit={handleLogin}>

                <div>
                    <label>Username: </label>
                    <input
                        type="text"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        required
                    />
                </div>

                <br />

                <div>
                    <label>Password: </label>
                    <input
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>

                <br />

                <button type="submit">
                    Login
                </button>

            </form>

            <p>{message}</p>
        </div>
    );
}

export default Login;