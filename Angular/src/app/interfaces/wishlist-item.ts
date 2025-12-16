import { Brand } from "./brand";

export interface WishlistItem {
    id: string;
    name: string;
    brand: Brand;
    price: number;
}
