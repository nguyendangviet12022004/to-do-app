import { CategoryDTO } from "./category.dto";
import { CommentDTO } from "./comment.dto";
import { StatusDTO } from "./status.dto";
import { TagDTO } from "./tag.dto";

export interface TaskDTO {
    id?: number;
    title?: string;
    description?: string;
    category?: CategoryDTO;
    tags?: TagDTO[];
    currentStatus?: StatusDTO;
    statusHistory?: StatusDTO[];
    comments?: CommentDTO[];
    attachments?: string[];
    priority?: number;
    createdDate?: string;
    modifiedDate?: string;
    dueDate?: string;
}

//  "id": 0,
//   "title": "",
//   "description": "",
//   "category": {},
//   "tags": [],
//   "currentStatus": {},
//   "statusHistory": [],
//   "comments": [],
//   "attachments": [],
//   "priority": 0,
//   "createdDate": "",
//   "modifiedDate": "",
//   "dueDate": ""