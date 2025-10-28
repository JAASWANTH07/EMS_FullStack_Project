
import EventDetail from "./event-detail.model";
import User from "./user.model";

interface Category {
  categoryId: number;
  createdOrganizerId: number;
  categoryName: string;
  createdBy?: User;               
  categoryEvents?: EventDetail;
}

export default Category;