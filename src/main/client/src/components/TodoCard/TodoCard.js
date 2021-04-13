import React from "react";
import "./TodoCard.css";

const TodoCard = function({task, completed, todoId, deleteTodo, updateTodo}) {
    const handleDeleteTodo = function() {
        deleteTodo(todoId);
    };

    const handleUpdateTodo = function() {
        updateTodo(todoId, task, !completed);
    };

    return (
        <div className="TodoCard">
            <div className="card shadow">
                <div className="card-body">
                    <div className="d-flex justify-content-between">
                        <p className="card-text">{task}</p>
                        <button type="button" className="btn-close" aria-label="delete" onClick={handleDeleteTodo}></button>
                    </div>
                    <p className="card-text completed-field" onClick={handleUpdateTodo}>{completed ? "Completed" : "Not Completed"}</p>
                </div>
            </div>
        </div>
    );
}

export default TodoCard;