import React, {useState, useEffect, useContext} from "react";
import {Redirect, useHistory} from "react-router-dom";
import axios from "axios";
import {AuthContext} from "../../auth/auth";
import TodoCard from "../TodoCard";

const Home = function() {
    const [task, setTask] = useState("");
    const [completed, setCompleted] = useState(false);
    const [errors, setErrors] = useState({});
    const [todos, setTodos] = useState([]);
    const {user, logoutUser} = useContext(AuthContext);
    const history = useHistory();

    const renderTodos = function() {
        axios.get("/api/todos")
        .then(function(response) {
            setTodos(response.data);
        }).catch(function(error) {
            if (error.response.status === 401) {
                logoutUser(history);
            }
        });
    };

    const deleteTodo = function(todoId) {
        axios.delete(`/api/todos/${todoId}`)
        .then(function() {
            renderTodos();
        })
        .catch(function(error) {
            if (error.response.status === 401) {
                logoutUser(history);
            }
        });
    }

    const updateTodo = function(todoId, task, completed) {
        const {userId} = user;
        const updatedTodo = {
            todoId,
            userId,
            task,
            completed
        }
        axios.put("/api/todos", updatedTodo)
        .then(function() {
            renderTodos();
        })
        .catch(function(error) {
            if (error.response.status === 401) {
                logoutUser(history);
            }
        })
    }

    const handleChangeTask = function(event) {
        setTask(event.target.value);
    }

    const handleChangeCompleted = function() {
        setCompleted(!completed);
    }

    const handleSubmit = function(event) {
        event.preventDefault();
        const {userId} = user;
        const newTodo = {
            userId,
            task,
            completed
        };
        axios.post("/api/todos", newTodo)
        .then(function(response) {
            setErrors({});
            setTask("");
            setCompleted(false);
            renderTodos();
        }).catch(function(error) {
            if (error.response.data.messages) {
                setErrors(error.response.data.messages);
            }
        });
    }

    useEffect(() => {
        renderTodos();
        // eslint-disable-next-line
    }, []);

    return (
        user
        ? <div className="Home">
            <div className="container">
                <div className="row mb-3">
                    <div className="col-12">
                        <h1 className="text-center">{`Welcome ${user.firstName} ${user.lastName}!`}</h1>
                    </div>
                </div>
                <div className="row">
                    <div className="col-6">
                        <div className="card shadow">
                            <div className="card-body">
                                <h5 className="card-title text-center mb-3">Add a new todo</h5>
                                <form onSubmit={handleSubmit}>
                                    {errors.todoId && <div className="alert alert-danger" role="alert">{errors.todoId}</div>}
                                    {errors.userId && <div className="alert alert-danger" role="alert">{errors.userId}</div>}
                                    <div className="form-floating mb-1">
                                        <input type="text" className={`form-control ${errors.task && "is-invalid"}`} id="taskInput" aria-describedby="taskInput" placeholder="example task" name="task" value={task} onChange={handleChangeTask} />
                                        <label htmlFor="exampleInputEmail1" className="form-label">Todo Description</label>
                                        {errors.task && <div id="emailHelp" className="form-text text-danger">{errors.task}</div>}
                                    </div>
                                    <div className="form-check mb-3">
                                        <input type="checkbox" className="form-check-input" id="completedInput" name="completed" value="" checked={completed} onChange={handleChangeCompleted} />
                                        <label htmlFor="completedInput" className="form-check-label">Completed?</label>
                                    </div>
                                    <button type="submit" className="btn btn-primary">Submit</button>
                                </form>
                            </div>
                        </div>
                    </div>
                    <div className="col-6">
                        {todos.map(todo => {
                            return <div className="row mb-3" key={todo.todoId}>
                                <div className="col-12">
                                    <TodoCard task={todo.task} completed={todo.completed} todoId={todo.todoId} deleteTodo={deleteTodo} updateTodo={updateTodo} />
                                </div>
                            </div>
                        })}
                    </div>
                </div>
            </div>
        </div>
        : <Redirect to="/login" />
    );
};

export default Home;